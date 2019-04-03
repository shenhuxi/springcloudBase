package com.dingxin.common.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @desc 导入导出常用方法
 **/

public class ExcelUtil  {

/**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * 如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
     * 如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写
     * fieldMap.put("college.collegeName","学院名称")
     * @param sheetName 工作表的名称
     * @param sheetSize 每个工作表中记录的最大个数
     * @param out       导出流
     * @throws ExcelException
     */

    public static <T>  void   listToExcel (
            List<T> list ,
            LinkedHashMap<String,String> fieldMap,
            String sheetName,
            int sheetSize,
            OutputStream out
    ) throws ExcelException{


        if(list.size()==0 || list==null){
            throw new ExcelException("数据源中没有任何数据");
        }

        if(sheetSize>65535 || sheetSize<1){
            sheetSize=65535;
        }

        //创建工作簿并发送到OutputStream指定的地方
        HSSFWorkbook wwb;
        try {
            wwb = new HSSFWorkbook();

            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
            //1.计算一共有多少个工作表
            double sheetNum=Math.ceil(list.size()/new Integer(sheetSize).doubleValue());

            //2.创建相应的工作表，并向其中填充数据
            for(int i=0; i<sheetNum; i++){
                //如果只有一个工作表的情况
                if(1==sheetNum){
                    HSSFSheet sheet = wwb.createSheet();
                    fillSheet(sheet, list, fieldMap, 0, list.size()-1);

                    //有多个工作表的情况
                }else{
                    HSSFSheet sheet=wwb.createSheet(sheetName+(i+1));

                    //获取开始索引和结束索引
                    int firstIndex=i*sheetSize;
                    int lastIndex=(i+1)*sheetSize-1>list.size()-1 ? list.size()-1 : (i+1)*sheetSize-1;
                    //填充工作表
                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
                }
            }

            wwb.write(out);
            wwb.close();

        }catch (Exception e) {
            e.printStackTrace();
            //如果是ExcelException，则直接抛出
            if(e instanceof ExcelException){
                throw (ExcelException)e;

                //否则将其它异常包装成ExcelException再抛出
            }else{
                throw new ExcelException("导出Excel失败");
            }
        }

    }

/**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param out       导出流
     * @throws ExcelException
     */

    public static  <T>  void   listToExcel (
            List<T> list ,
            LinkedHashMap<String,String> fieldMap,
            String sheetName,
            OutputStream out
    ) throws ExcelException{

        listToExcel(list, fieldMap, sheetName, 65535, out);

    }


/**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param sheetSize    每个工作表中记录的最大个数
     * @param response  使用response可以导出到浏览器
     * @throws ExcelException
     */

    public static  <T>  void   listToExcel (
            List<T> list ,
            LinkedHashMap<String,String> fieldMap,
            String sheetName,
            int sheetSize,
            HttpServletResponse response
    ) throws ExcelException{

        //设置默认文件名为当前时间：年月日时分秒
        String fileName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();

        //设置response头信息
        response.reset();
        response.setContentType("application/vnd.ms-excel");        //改成输出excel文件
        response.setHeader("Content-disposition","attachment; filename="+fileName+".xls" );

        //创建工作簿并发送到浏览器
        try {

            OutputStream out=response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, sheetSize,out );

        } catch (Exception e) {
            e.printStackTrace();

            //如果是ExcelException，则直接抛出
            if(e instanceof ExcelException){
                throw (ExcelException)e;

                //否则将其它异常包装成ExcelException再抛出
            }else{
                throw new ExcelException("导出Excel失败");
            }
        }
    }


/**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param response  使用response可以导出到浏览器
     * @throws ExcelException
     */

    public static <T>  void   listToExcel (
            List<T> list ,
            LinkedHashMap<String,String> fieldMap,
            String sheetName,
            HttpServletResponse response
    ) throws ExcelException{

        listToExcel(list, fieldMap, sheetName, 65535, response);
    }

/**
     * @MethodName          : excelToList
     * @Description             : 将Excel转化为List
     * @param in                    ：承载着Excel的输入流
     * @param sheetName        ：要导入的工作表序号
     * @param entityClass       ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param fieldMap          ：Excel中的中文列头和类的英文属性的对应关系Map
     * @param uniqueFields  ：指定业务主键组合（即复合主键），这些列的组合不能重复
     * @return                      ：List
     * @throws ExcelException
     */

    public static <T>  List<T>  excelToList(
            InputStream in,
            String sheetName,
            Class<T> entityClass,
            LinkedHashMap<String, String> fieldMap,
            String[] uniqueFields
    ) throws ExcelException{

        //定义要返回的list
        List<T> resultList=new ArrayList<T>();

        try {
            //根据Excel数据源创建WorkBook
            Workbook wb=new HSSFWorkbook(new POIFSFileSystem(in));
            //获取工作表
            Sheet sheet=wb.getSheet(sheetName);

            //获取工作表的有效行数
            int realRows=0;
            for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){

                int nullCols=0;
                Row row = sheet.getRow(i);
                if(row!=null){
                    int cells=row.getPhysicalNumberOfCells();
                    for(int c=0;c<cells;c++){
                        Cell currentCell = row.getCell(c);
                        if(currentCell==null || "".equals(currentCell.getStringCellValue())){
                            nullCols++;
                        }
                    }
                    if(nullCols==cells){
                        break;
                    }else{
                        realRows++;
                    }
                }
            }


            //如果Excel中没有数据则提示错误
            if(realRows<=1){
                throw new ExcelException("Excel文件中没有任何数据");
            }


            Row firstRow=sheet.getRow(0);
            int cells=firstRow.getPhysicalNumberOfCells();

            String[] excelFieldNames=new String[cells];

            //获取Excel中的列名
            for(int c=0;c<cells;c++){
                Cell currentCell = firstRow.getCell(c);
                excelFieldNames[c]=currentCell.getStringCellValue().trim();
            }

            //判断需要的字段在Excel中是否都存在
            boolean isExist=true;
            List<String> excelFieldList=Arrays.asList(excelFieldNames);
            for(String cnName : fieldMap.keySet()){
                if(!excelFieldList.contains(cnName)){
                    isExist=false;
                    break;
                }
            }

            //如果有列名不存在，则抛出异常，提示错误
            if(!isExist){
                throw new ExcelException("Excel中缺少必要的字段，或字段名称有误");
            }


            //将列名和列号放入Map中,这样通过列名就可以拿到列号
            LinkedHashMap<String, Integer> colMap=new LinkedHashMap<String, Integer>();
            for(int i=0;i<excelFieldNames.length;i++){
                Cell currentCell = firstRow.getCell(i);
                colMap.put(excelFieldNames[i], currentCell.getColumnIndex());
            }


            //将sheet转换为list
            for(int i=1;i<realRows;i++){
                //新建要转换的对象
                T entity=entityClass.newInstance();

                //给对象中的字段赋值
                for(Map.Entry<String, String> entry : fieldMap.entrySet()){
                    //获取中文字段名
                    String cnNormalName=entry.getKey();
                    //获取英文字段名
                    String enNormalName=entry.getValue();
                    //根据中文字段名获取列号
                    int col=colMap.get(cnNormalName);

                    //获取当前单元格中的内容
                    Row row = sheet.getRow(i);
                    Cell currentCell = row.getCell(col);
                    String content=currentCell.getStringCellValue();

                    //给对象赋值
                    setFieldValueByName(enNormalName, content, entity);
                }

                resultList.add(entity);
            }
        } catch(Exception e){
            e.printStackTrace();
            //如果是ExcelException，则直接抛出
            if(e instanceof ExcelException){
                throw (ExcelException)e;

                //否则将其它异常包装成ExcelException再抛出
            }else{
                e.printStackTrace();
                throw new ExcelException("导入Excel失败");
            }
        }

        List<T> tempList= new ArrayList<T>();

        for(T i:resultList){
            if(!tempList.contains(i)){
                tempList.add(i);
            }
        }
        return resultList;
    }



/*<-------------------------辅助的私有方法----------------------------------------------->*/

/**
     * @MethodName  : getFieldValueByName
     * @Description : 根据字段名获取字段值
     * @param fieldName 字段名
     * @param o 对象
     * @return  字段值
     */

    private static  Object getFieldValueByName(String fieldName, Object o) throws Exception{

        Object value=null;
        Field field=getFieldByName(fieldName, o.getClass());

        if(field !=null){
            field.setAccessible(true);
            value=field.get(o);
        }else{
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
        }

        return value;
    }

/**
     * @MethodName  : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */

    private static Field getFieldByName(String fieldName, Class<?>  clazz){
        //拿到本类的所有字段
        Field[] selfFields=clazz.getDeclaredFields();

        //如果本类中存在该字段，则返回
        for(Field field : selfFields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz=clazz.getSuperclass();
        if(superClazz!=null  &&  superClazz !=Object.class){
            return getFieldByName(fieldName, superClazz);
        }

        //如果本类和父类都没有，则返回空
        return null;
    }



/**
     * @MethodName  : getFieldValueByNameSequence
     * @Description :
     * 根据带路径或不带路径的属性名获取属性值
     * 即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence  带路径的属性名或简单属性名
     * @param o 对象
     * @return  属性值
     * @throws Exception
     */

    private static  Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception{

        Object value=null;

        //将fieldNameSequence进行拆分
        String[] attributes=fieldNameSequence.split("\\.");
        if(attributes.length==1){
            value=getFieldValueByName(fieldNameSequence, o);
        }else{
            //根据属性名获取属性对象
            Object fieldObj=getFieldValueByName(attributes[0], o);
            String subFieldNameSequence=fieldNameSequence.substring(fieldNameSequence.indexOf(".")+1);
            value=getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;

    }


/**
     * @MethodName  : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param fieldValue    字段值
     * @param o 对象
     */

    private static void setFieldValueByName(String fieldName,Object fieldValue,Object o) throws Exception{

        Field field=getFieldByName(fieldName, o.getClass());
        if(field!=null){
            field.setAccessible(true);
            //获取字段类型
            Class<?> fieldType = field.getType();

            //根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType)
                    || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType)
                    || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType)
                    || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType)
                    || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue!= null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, Character
                            .valueOf(fieldValue.toString().charAt(0)));
                }
            }else if(Date.class==fieldType){
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            }else{
                field.set(o, fieldValue);
            }
        }else{
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
        }
    }


/**
     * @MethodName  : setColumnAutoSize
     * @Description : 设置工作表自动列宽和首行加粗
     * @param ws
     */

    private static void setColumnAutoSize(Sheet ws,int extraWith) {
        //获取本列的最宽单元格的宽度
        int rows = ws.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            int colWith = 0;
            Row row = ws.getRow(i);
            if (row != null) {
                int cells = row.getPhysicalNumberOfCells();
                for (int c = 0; c < cells; c++) {
                    Cell cell = row.getCell(c);
                    String content = cell.getStringCellValue();
                    int cellWith = content.length();
                    if (colWith < cellWith) {
                        colWith = cellWith;
                    }
                }
            }
            //设置单元格的宽度为最宽宽度+额外宽度
            ws.setColumnWidth(i, colWith + extraWith);
        }
    }
/**
     * @MethodName  : fillSheet
     * @Description : 向工作表中填充数据
     * @param sheet     工作表
     * @param list  数据源
     * @param fieldMap 中英文字段对应关系的Map
     * @param firstIndex    开始索引
     * @param lastIndex 结束索引
     */

    private static <T> void fillSheet(
            HSSFSheet sheet,
            List<T> list,
            LinkedHashMap<String,String> fieldMap,
            int firstIndex,
            int lastIndex
    )throws Exception{

        //定义存放英文字段名和中文字段名的数组
        String[] enFields=new String[fieldMap.size()];
        String[] cnFields=new String[fieldMap.size()];

        //填充数组
        int count=0;
        for(Map.Entry<String,String> entry:fieldMap.entrySet()){
            enFields[count]=entry.getKey();
            cnFields[count]=entry.getValue();
            count++;
        }
        //填充表头
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<cnFields.length;i++){
            Cell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(cnFields[i]);
            cell.setCellValue(text.toString());
        }

        //填充内容
        int rowNo=1;
        for(int index=firstIndex;index<=lastIndex;index++){
            //获取单个对象
            T item=list.get(index);
            for(int i=0;i<enFields.length;i++){
                Object objValue=getFieldValueByNameSequence(enFields[i], item);
                String fieldValue=objValue==null ? "" : objValue.toString();
                HSSFRow currentRow = sheet.createRow(rowNo);
                Cell cell = currentRow.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(fieldValue);
                cell.setCellValue(text.toString());
            }

            rowNo++;
        }

        //设置自动列宽
//        setColumnAutoSize(sheet, 5);
    }

}