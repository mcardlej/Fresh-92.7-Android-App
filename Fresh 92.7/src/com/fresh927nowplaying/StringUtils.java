package com.fresh927nowplaying;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	public static String join(Collection<?> s, String delimiter) {
	    StringBuffer buffer = new StringBuffer();
	    Iterator<?> iter = s.iterator();
	    while (iter.hasNext()) {
	        buffer.append(iter.next());
	        if (iter.hasNext()) {
	            buffer.append(delimiter);
	        }
	    }
	    return buffer.toString();
	}
	
	
}
