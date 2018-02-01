package org.mes.ipv4converter.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mes.ipv4converter.Ipv4Converter;
import org.mes.ipv4converter.Ipv4ConverterException;

/**
 * 
 * @author mestebansosa
 *
 */
public class Ipv4ConverterTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void minIp() throws NumberFormatException, Ipv4ConverterException {
		String ipString = "0.0.0.0";
		long ipNumber = 0;
		assertTrue("ipString " + ipString + " should be equal to ipNumber " + ipNumber, Ipv4Converter.getIpNumber(ipString) == ipNumber);
		assertTrue("ipNumber " + ipNumber + " should be equal to ipString " + ipString, Ipv4Converter.getIpString(ipNumber).contentEquals(ipString));
	}

	@Test
	public void maxIp() throws NumberFormatException, Ipv4ConverterException {
		String ipString = "255.255.255.255";
		long ipNumber = Ipv4Converter.blockA - 1;
		assertTrue("ipString " + ipString + " should be equal to ipNumber " + ipNumber, Ipv4Converter.getIpNumber(ipString) == ipNumber);
		assertTrue("ipNumber " + ipNumber + " should be equal to ipString " + ipString, Ipv4Converter.getIpString(ipNumber).contentEquals(ipString));
	}
	
	@Test(expected = NumberFormatException.class)
	public void getIpNumberError() throws NumberFormatException, Ipv4ConverterException {
		String ipString = "2b5.255.255.255";
		Ipv4Converter.getIpNumber(ipString);
	}
		
	@Test(expected = Ipv4ConverterException.class)
	public void getIpStringError() throws NumberFormatException, Ipv4ConverterException {
		long ipNumber = Ipv4Converter.blockA + 1;
		Ipv4Converter.getIpString(ipNumber);
	}
	
	@Test(expected = Ipv4ConverterException.class)
	public void getIpBlockError() throws Ipv4ConverterException {
		String ipString = "255.255.255.255";
		char position = 'z';
		Ipv4Converter.getIpBlock(position, ipString);
	}
	
	@Test
	public void getIpBlock1() throws Ipv4ConverterException {
		// getIpBlock('d',"0.0.0.255") returns 255 while getIpBlock('A',"225.0.0.0") returns 4278190080
		char position = 'd';
		String ipString = "0.0.0.255";
		long ipNumber = 255;
		assertTrue("getIpBlock('"+ position + "',\"" + ipString + "\") should be equal to " + ipNumber, Ipv4Converter.getIpBlock(position, ipString) == ipNumber);
		position = 'A';
		ipString = "255.0.0.0";
		ipNumber = 4278190080L;
		assertTrue("getIpBlock('"+ position + "',\"" + ipString + "\") should be equal to " + ipNumber, Ipv4Converter.getIpBlock(position, ipString) == ipNumber);
	}

	@Test
	public void getIpBlock2() throws Ipv4ConverterException {
		// getIpBlock('d',255) returns 255 while getIpBlock('a',4278190080) returns 255;
		char position = 'd';
		long ipNumber = 255;
		long result = 255;
		assertTrue("getIpBlock('"+ position + "',\"" + ipNumber + "\") should be equal to " + result, Ipv4Converter.getIpBlock(position, ipNumber) == result);
		position = 'a';
		ipNumber = 4278190080L;
		result = 255;
		assertTrue("getIpBlock('"+ position + "',\"" + ipNumber + "\") should be equal to " + result, Ipv4Converter.getIpBlock(position, ipNumber) == result);
	}

	@Test
	public void speed() throws Ipv4ConverterException {
		String ipString = "255.255.255.255";
		String resultString = "";
		long resultLong = 0;
		long ipNumber = Ipv4Converter.blockA - 1;
		long start = 0;
		long end = 0;
		double getIpStringGather = 0;
		double getIpNumberGather = 0;
		int times = 50;

		for (int x = 0; x < times; x++) {
			start = System.nanoTime();
			resultString = Ipv4Converter.getIpString(ipNumber);
			end = System.nanoTime();
			getIpStringGather += (end - start);
			System.out.println(String.format("getIpString(%d)=%s lasted %d nanoseconds %f milliseconds", ipNumber,
					resultString, (end - start), ((double) end - start) / 1_000_000));
			start = System.nanoTime();
			resultLong = Ipv4Converter.getIpNumber(ipString);
			end = System.nanoTime();
			getIpNumberGather += (end - start);
			System.out.println(String.format("getIpNumber(%s)=%d lasted %d nanoseconds %f milliseconds", ipString,
					resultLong, (end - start), ((double) end - start) / 1_000_000));
		}
		getIpStringGather = getIpStringGather / times;
		System.out.println(String.format("getIpString(%d) executed %d times took %f nanoseconds %f milliseconds",
				ipNumber, times, getIpStringGather, ((double) getIpStringGather) / 1_000_000));
		getIpNumberGather = getIpNumberGather / times;
		System.out.println(String.format("getIpNumber(%s) executed %d times took %f nanoseconds %f milliseconds",
				ipString, times, getIpNumberGather, ((double) getIpNumberGather) / 1_000_000));
	}

}
