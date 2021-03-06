package com.illucit.partyinvoice;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.illucit.partyinvoice.xmldata.XmlProject;

/**
 * Input/output helper for XML project files.
 * 
 * @author Christian Simon
 *
 */
public class XmlIO {

	/**
	 * Save {@link XmlProject} to XML file.
	 * 
	 * @param targetFile
	 *            target file to save to
	 * @param project
	 *            XML project instance
	 * @throws JAXBException
	 *             if saving causes an error
	 */
	public static void saveToFile(File targetFile, XmlProject project) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(XmlProject.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(project, targetFile);
	}

	/**
	 * Load {@link XmlProject} from XML file.
	 * 
	 * @param targetFile
	 *            target file to load from
	 * @return loaded XML project
	 * @throws JAXBException
	 *             if loading causes an error
	 */
	public static XmlProject loadFromFile(File targetFile) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(XmlProject.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (XmlProject) unmarshaller.unmarshal(targetFile);
	}

}
