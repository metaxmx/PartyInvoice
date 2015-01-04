package com.illucit.partyinvoice;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.illucit.partyinvoice.xmldata.Project;

/**
 * Input/output helper for XML project files.
 * 
 * @author Christian Simon
 *
 */
public class XmlIO {

	public static void saveToFile(File targetFile, Project project) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Project.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(project, targetFile);
	}

	public static Project loadFromFile(File targetFile) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Project.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (Project) unmarshaller.unmarshal(targetFile);
	}

}
