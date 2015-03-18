package eu.europa.ec.markt.dss.validation102853.policy;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ValidationPolicyTest {

	@Test
	public void test1() throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		byte[] data = IOUtils.toByteArray(getClass().getResourceAsStream("/102853/policy/constraint.xml"));

		EtsiValidationPolicy policy = new EtsiValidationPolicy(dbf.newDocumentBuilder().parse(new ByteArrayInputStream(data)));

	}

}