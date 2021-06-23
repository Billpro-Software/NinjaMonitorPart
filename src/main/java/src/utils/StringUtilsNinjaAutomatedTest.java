package src.utils;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class StringUtilsNinjaAutomatedTest {

    public static final String makeLength(final String str, final char ch, final int len) {
        return makeLength1(str, ch, len, false);
    }

    public static final String makeLength1(final String str,
                                           final char ch,
                                           final int len,
                                           final boolean bPrefix) {
        return makeLength2(new StringBuffer(len), str, ch, len, bPrefix).toString();
    }

    public static final StringBuffer makeLength2(final StringBuffer sb,
                                                 final String str,
                                                 final char ch,
                                                 final int len,
                                                 final boolean bPrefix) {
        // If we're not prefixing, append the string to the stringbuffer here.
        if (!bPrefix) {
            sb.append(str);
        }

        // Add the specified char until the specifed length is reached.
        for (int i = 0, n = len - str.length(); i < n; i++) {
            sb.append(ch);
        }

        // Since we already added the specified char,
        // all we need to do is append the specified string.
        if (bPrefix) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * Concatenates s1, s2 & s3 to one
     */
    public static final String concat(String s1, String s2, String s3) {
        return new StringBuffer(strlen(s1) +
                strlen(s2) +
                strlen(s3))
                .append(s1).append(s2).append(s3).toString();
    }

    private static final int strlen(final String s) {
        return s == null ? 4 : s.length();
    }


    public static final boolean isNullOrEmpty(final String s) {
        return s == null ? true : s.length() == 0;
    }


    public static final boolean isNotNullOrEmpty(final String s) {
        return (!isNullOrEmpty(s));
    }


    public static final String strAfterStr(String strInput, String strSearch) {
        String strOutput = "";
        String strNotFound = null;

        if (isNullOrEmpty(strInput)) {
            return strNotFound;
        }
        if (isNullOrEmpty(strSearch)) {
            return strNotFound;
        }

        int i = strInput.indexOf(strSearch);
        if (i == -1) {
            return strNotFound;
        }

        i = i + strSearch.length();

        strOutput = strInput.substring(i);

        return strOutput;
    }

    public static final String strBeforeStr(String strInput, String strSearch) {
        String strOutput = "";
        String strNotFound = null;

        if (isNullOrEmpty(strInput)) {
            return strNotFound;
        }
        if (isNullOrEmpty(strSearch)) {
            return strNotFound;
        }

        int i = strInput.indexOf(strSearch);
        if (i == -1) {
            return strNotFound;
        }

        strOutput = strInput.substring(0, i);

        return strOutput;
    }

    public static final String strBetweenStr(String strInput, String strSearch1, String strSearch2) {
        String strOutput;

        strOutput = strAfterStr(strInput, strSearch1);

        strOutput = strBeforeStr(strOutput, strSearch2);

        return strOutput;
    }

    public static final String strGetXmlTagValue(String strXml, String tagName) {
        String strOutput = null;

        if (tagName.contains("->")) {
            String headTagName = StringUtilsNinjaAutomatedTest.strBeforeStr(tagName, "->");
            String tailTagName = StringUtilsNinjaAutomatedTest.strAfterStr(tagName, "->");

            String tagValue = strGetXmlTagValue(strXml, headTagName);

            return strGetXmlTagValue(tagValue, tailTagName);
        }

        if (tagName.matches("^ns\\d+:(.*)")) {

            strOutput = strFilterXmlRemarks(strXml);

            strOutput = StringUtilsNinjaAutomatedTest.strAfterStr(strOutput, "<" + tagName);

            strOutput = StringUtilsNinjaAutomatedTest.strAfterStr(strOutput, ">");

            strOutput = StringUtilsNinjaAutomatedTest.strBeforeStr(strOutput, "</" + tagName + ">");

        } else {
            String realTagName = strFindXmlRealTagName(strXml,tagName);
            String tagStartInXml = strFindXmlTag(strXml, tagName);

            String tagEndInXml = strFindXmlEndTag(strXml, realTagName);

            strOutput = strFilterXmlRemarks(strXml);

            /*
            strOutput = StringUtilsNinjaAutomatedTest.strAfterStr(strOutput, tagNameInXml);

            strOutput = StringUtilsNinjaAutomatedTest.strAfterStr(strOutput, ">");

            strOutput = StringUtilsNinjaAutomatedTest.strBeforeStr(strOutput, tagName);

            strOutput = StringUtilsNinjaAutomatedTest.strReverse(strOutput);

            strOutput = StringUtilsNinjaAutomatedTest.strAfterStr(strOutput, "/<");

            strOutput = StringUtilsNinjaAutomatedTest.strReverse(strOutput);
            */
            strOutput = StringUtilsNinjaAutomatedTest.strBetweenStr(strOutput, tagStartInXml, tagEndInXml);

        }


        return strOutput;
    }

    public static final String strFilterXmlRemarks(String strXml) {

        if (strXml == null) {
            return null;
        }

        String strOutput = strXml;

        while (strOutput.contains("<!--")) {
            strOutput = strBeforeStr(strOutput, "<!--") + strAfterStr(strOutput, "-->");
        }
        return strOutput;
    }

    public static final String strFindXmlRealTagName(String strXml, String strTagName) {
        String ans = strBetweenStr(strFindXmlTag(strXml, strTagName), "<", ">");

        if (ans != null) {
            if (ans.contains(" ")) {
                ans = strBeforeStr(ans, " ");
            }
        }
        return ans;
    }


    public static final String strFindXmlTag(String strXml, String strInputTagName) {

        String strTemp = strFilterXmlRemarks(strXml);

        while (strTemp != null && strTemp.contains("<")) {
            String strTag = strTag = "<" + strBetweenStr(strTemp, "<", ">") + ">";

            String strCurrentTagName = strBetweenStr(strTag, "<", ">");

            if (strCurrentTagName.contains(" ")) {
                strCurrentTagName = strBeforeStr(strCurrentTagName, " ");
            }

            if (strCurrentTagName.contains(":")) {
                strCurrentTagName = strAfterStr(strCurrentTagName, ":");
            }


            if (strCurrentTagName.equals(strInputTagName)) {
                return strTag;
            }

            //if (strCurrentTagName.contains(strInputTagName) && !strCurrentTagName.contains("xmlns")) {
            //    return strTag;
            //}
            strTemp = strAfterStr(strTemp, strTag);

        }
        return null;
    }

    public static final String strFindXmlEndTag(String strXml, String strTagName) {

        String strTemp = strFilterXmlRemarks(strXml);
        String strTag = null;

        while (strTemp != null && strTemp.contains("<")) {
            strTag = "<" + strBetweenStr(strTemp, "<", ">") + ">";

            if (strTag.equals("</" + strTagName + ">")) {
                return strTag;
            }

            //if (strTag.contains("/") && strTag.contains(strTagName) && !strTag.contains("xmlns")) {
            //    return strTag;
            //}

            strTemp = strAfterStr(strTemp, strTag);
        }
        return strTag;
    }


    public static final String strReverse(String str) {

        String reverse = "";


        for (int i = str.length() - 1; i >= 0; i--) {
            reverse = reverse + str.charAt(i);
        }
        return reverse;
    }

    public static final String listToSqlString(List<String> list) {
        String strOutput = "";
        boolean isFirstItem = true;
        for (String string : list) {

            if (string == null) {
                continue;
            }

            if (isFirstItem) {
                isFirstItem = false;
            } else {
                strOutput = strOutput + ", ";
            }

            strOutput = strOutput + "'" + string.trim() + "'";

        }
        return strOutput;
    }


    public static final String strFormat047(String strSubscriberNo) {
        String strOutput = "";

        if (strSubscriberNo.startsWith("GSM047")) {
            return strAfterStr(strSubscriberNo, "GSM");
        }

        if (strSubscriberNo.startsWith("GSM47")) {
            return "0" + strAfterStr(strSubscriberNo, "GSM");
        }

        if (strSubscriberNo.startsWith("CDA047")) {
            return strAfterStr(strSubscriberNo, "CDA");
        }

        if (strSubscriberNo.startsWith("047")) {
            return strSubscriberNo;
        }

        if (strSubscriberNo.startsWith("47")) {

            if (strSubscriberNo.length() == 8) {
                // this is the short format , so we will add the 047
                return "047" + strSubscriberNo;
            } else {
                return "0" + strSubscriberNo;
            }
        }

        // Default case add the missing 047
        return "047" + strSubscriberNo;

    }

    public static final String strReplace(String strInput, String strSearch, String strReplace) {
        return strTranslate(strInput, strSearch, strReplace);
    }


    public static final String strTranslate(String strInput, String strSearch, String strReplace) {
        String strOutput = strInput;

        while (strOutput.contains(strSearch)) {
            strOutput = strBeforeStr(strOutput, strSearch) + strReplace + strAfterStr(strOutput, strSearch);
        }

        return strOutput;
    }

    public static final String strTranslateFileSeperator(String strDir) {
        return strTranslate(strDir, "\\", "/");
    }

    public static String f(String s) {

        String strResult = "";

        if (isNullOrEmpty(s)) {
            return strResult;
        }
        int sLen = s.length();
        int i = 0;
        int j = 0;

        String subStr = String.valueOf(s.charAt(i));
        strResult = subStr;

        Set<String> setSubStr = new HashSet<>();
        setSubStr.add(subStr);

        while (j < sLen) {
            if (i == j) {
                j++;
                continue;
            }
            String c = String.valueOf(s.charAt(j));
            if (setSubStr.contains(c)) {
                // duplicate found , update sub string cut head with duplicate, add the duplicate char to the end of the sub string
                // check if new max found and store it ....
                if (subStr.length() > strResult.length()) {
                    strResult = subStr;
                }
                // cut the head with the duplicate
                while (!(String.valueOf(s.charAt(i)).equals(c))) {
                    String charToDelete = String.valueOf(s.charAt(i));
                    setSubStr.remove(charToDelete);
                    i++;
                }
                String charToDelete = String.valueOf(s.charAt(i));
                setSubStr.remove(charToDelete);
                i++; // one place after the duplicate ...
                // add the duplicate at the end of subStr
                j++;
                subStr = s.substring(i, j);
                setSubStr.add(c);
            } else {
                // Last char is not duplicate , add it to the end of subStr
                j++;
                subStr = s.substring(i, j);
                setSubStr.add(c);
            }
        }

        // check if new max found and store it , this is for case that our sub string is in the end of the input string
        if (subStr.length() > strResult.length()) {
            strResult = subStr;
        }

        return strResult;
    }


    public static int g(String s) {
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        while (j < n) {
            Character c = s.charAt(j);
            if (!set.contains(c)) {
                // extend set
                set.add(c);
            } else {
                ans = Math.max(ans, j - i - 1);
                // shrink set
                while (!c.equals(s.charAt(i))) {
                    set.remove(s.charAt(i));
                    i++;
                }
                i++;// now duplicate is dropped ...
            }
            j++;
        }
        ans = Math.max(ans, j - i);
        return ans;
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }


    public static final String strRichardXmlToNormalXml(String strXml) {
        Stack<String> stack = new Stack<>();

        String strTemp = strFilterXmlRemarks(strXml);
        String xmlOut = "";
        String strTag = null;
        String strHead = null;

        while (strTemp != null && strTemp.contains("<")) {
            strTag = "<" + strBetweenStr(strTemp, "<", ">") + ">";
            strHead = strBeforeStr(strTemp, strTag);
            xmlOut += strHead;
            if (strTag.startsWith("<parameter name=")) {
                String strNewTag = strBetweenStr(strTag, "\"", "\"");
                stack.push(strNewTag);
                xmlOut += "<" + strNewTag + ">";
            } else if (strTag.startsWith("</parameter")) {
                xmlOut += "</" + stack.pop() + ">";
            } else {
                xmlOut += strTag;
            }
            strTemp = strAfterStr(strTemp, strTag);
        }
        return xmlOut;
    }





}
