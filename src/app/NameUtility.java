/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author sarra
 */
public class NameUtility {
    /**
	 * 
	 * @param tagName
	 * @return
	 */
	public static String getValidClassNameForTagName(String tagName) {
		String classname = "";
		String[] subStrings = tagName.split("-");
		for (int i = 0; i < subStrings.length; i++) {
		
			subStrings[i] = subStrings[i].replaceFirst(String.valueOf(subStrings[i].charAt(0)),
					String.valueOf(Character.toUpperCase(subStrings[i].charAt(0))));
			classname = classname + subStrings[i];
		}
		return classname;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getValidMemberName(String name) {
		return name.replaceFirst(String.valueOf(name.charAt(0)),
				String.valueOf(Character.toLowerCase(name.charAt(0))));
	}
	
	/**
	 * 
	 * @param variableName
	 * @return
	 */
	public static String getSetMethodName(String variableName) {
		String setMethodName = "";
		setMethodName = "set"+variableName;
		return setMethodName;
	}
}
