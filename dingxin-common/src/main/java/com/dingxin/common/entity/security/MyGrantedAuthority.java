package com.dingxin.common.entity.security;

import org.springframework.security.core.GrantedAuthority;


public class MyGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 7302545071040537713L;
	private final String url;
    private final String method;

    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }


	public String getUrl() {
		return url;
	}


	public String getMethod() {
		return method;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		MyGrantedAuthority other = (MyGrantedAuthority) obj;
		if (method == null) {
			if (other.method != null) {
                return false;
            }
		} else if (!method.equals(other.method)) {
            return false;
        }
		if (url == null) {
			if (other.url != null) {
                return false;
            }
		} else if (!url.equals(other.url)) {
            return false;
        }
		return true;
	}

	
	
	@Override
	public String toString() {
		return "MyGrantedAuthority [url=" + this.url + ", method=" + this.method + "]";
	}


	@Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}