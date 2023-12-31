package com.gluk.z2j.app;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.gluk.z2j.model.AbstractDoc;

public class Serializer extends AbstractDoc {
	
	private String name;
	
	public Serializer(String dir, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(dir);
		sb.append("/");
		int ix = name.lastIndexOf('.');
		if (ix > 0) {
			sb.append(name.substring(0, ix));
		} else {
			sb.append(name);
		}
		sb.append(".xml");
		this.name = sb.toString();
	}
	
	public TransformerHandler getHandler() throws Exception {
		init();
		return handler;
	}
	
    protected void init() throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		if (tf.getFeature(SAXSource.FEATURE) && tf.getFeature(SAXResult.FEATURE)) {
	        SAXTransformerFactory stf = (SAXTransformerFactory)tf;
	        handler = stf.newTransformerHandler();
            Result result = new StreamResult(new File(name));
            handler.setResult(result);
		} else {
			throw new Exception("Feature unsupported");
		}
    }
    
    public void load(Document in) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		if (tf.getFeature(DOMSource.FEATURE) && tf.getFeature(SAXResult.FEATURE)) {
			DOMSource source = new DOMSource(in);
            Result result = new StreamResult(new File(name));
            Transformer t = tf.newTransformer();
            t.transform(source, result);
		} else {
			throw new Exception("Feature unsupported");
		}
    }
}
