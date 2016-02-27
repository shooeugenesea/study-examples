package dsaa.stack;

import java.io.IOException;
import java.util.Scanner;


public class ValidateXMLElmtPair {

	public static boolean isValid(String xml) throws IOException {
		XMLElmtStack stack = new XMLElmtStack();
		try (Scanner s = new Scanner(xml) ) {
			String tag;
			while (true) {
				while ((tag = s.findInLine("<[^>]*>")) != null) {
					if (!isValidTag(stack, tag)) {
						return false;
					}
				}
				if ( s.hasNextLine() ) {
					s.nextLine();
				} else {
					break;
				}
			}			
		}
		return true;
	}

	private static boolean isValidTag(XMLElmtStack stack, String tag) {
		String tagName = tagName(tag);
		if ( isAloneTag(tag) ) {
			return true;
		} else if ( isEndTag(tag) ) {
			String popTagName = stack.pop();
			if ( popTagName == null || !tagName.equals(popTagName) ) {
				System.out.println("invalid. tagName: " + tagName + ", popTagName:" + popTagName);
				return false;
			} 
		} else {
			stack.push(tagName);
		}
		return true;
	}
	
	private static String tagName(String tag) {
		tag = tag.substring(1, tag.length()-1);
		if ( tag.startsWith("/") ) {
			 tag = tag.substring(1);
		}
		return tag;
	}
	
	/**
	 * <tag />
	 * */
	private static boolean isAloneTag(String tag) {
		return tag.endsWith("/>");
	}
	
	/**
	 * </ tag>
	 * */
	private static boolean isEndTag(String tag) {
		return tag.startsWith("</");
	}
	
	static class XMLElmtStack {
		private Node tail = new Node(null);
		
		public void push(String s) {
			Node preElmt = tail.getPrev();
			Node newElmt = new Node(s);
			newElmt.setPrev(preElmt);
			tail.setPrev(newElmt);
		}
		
		public String top() {
			Node prev = tail.getPrev();
			if ( prev == null ) {
				return null;
			}
			return prev.getVal();
		}
		
		public String pop() {
			Node prev = tail.getPrev();
			if ( prev == null ) {
				return null;
			}
			Node prePrev = prev.getPrev();
			tail.setPrev(prePrev);
			prev.setPrev(null);
			return prev.getVal();
		}
	}
	
	private static class Node {
		private String val;
		private Node prev;
		public Node(String val) {
			this.val = val;
		}
		public void setPrev(Node prev) {
			this.prev = prev;
		}
		public Node getPrev() {
			return prev;
		}
		public String getVal() {
			return val;
		}
	}
	
	
}
