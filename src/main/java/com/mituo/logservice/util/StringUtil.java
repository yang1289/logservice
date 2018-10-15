package com.mituo.logservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    public String clearBlankWrapTabs(String oldString){
        String newString="";
        if(!"".equals(oldString) && null!=oldString){
            Pattern pattern=Pattern.compile("\\s*|\t|\n|\r");
            Matcher matcher=pattern.matcher(oldString);
            newString=matcher.replaceAll("");
        }
        return newString;
    }

    public String getSubString(String dataString,String regex,int groupIndex){
        String subString="";
        if(!"".equals(dataString) && null!=dataString && !"".equals(regex) && null!=regex){
            Pattern pattern=Pattern.compile(regex);
            Matcher matcher=pattern.matcher(dataString);
            if(matcher.find()){
                subString=matcher.group(groupIndex);
            }
        }
        return subString;
    }

}
