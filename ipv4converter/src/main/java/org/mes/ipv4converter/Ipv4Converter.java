package org.mes.ipv4converter;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Converts an ipv4 string (blockA.blockB.blockC.blockD) to an unique ipv4 long value and viceversa.
 * Max value = 256*256*256*256 - 1 (2^32 -1) Note that max int primitive is 2^31 - 1
 * @author mestebansosa
 */
public final class Ipv4Converter {
	final public static long blockD = 256;
	final public static long blockC = blockD * 256;
	final public static long blockB = blockC * 256;
	final public static long blockA = blockB * 256;

	/**
	 * Converts an ipv4 long value to an unique ipv4 string
	 * (blockA.blockB.blockC.blockD)
	 * 
	 * @param ipNumber
	 * @return ipString
	 * @throws Ipv4ConverterException
	 */
	public static String getIpString(long ipNumber) throws Ipv4ConverterException {
		return Ipv4Converter.getIpBlock('A', ipNumber) + "." + Ipv4Converter.getIpBlock('B', ipNumber) + "."
				+ Ipv4Converter.getIpBlock('C', ipNumber) + "." + Ipv4Converter.getIpBlock('D', ipNumber);
		
/*		try {
			return new StringBuilder().append(Ipv4Converter.getIpBlock('A', ipNumber)).append('.')
					.append(Ipv4Converter.getIpBlock('B', ipNumber)).append('.')
					.append(Ipv4Converter.getIpBlock('C', ipNumber)).append('.')
					.append(Ipv4Converter.getIpBlock('D', ipNumber)).toString();
		} catch (Ipv4ConverterException n) {
			throw n;
		} catch (Exception e) {
			return null;
		}
*/	};

	/**
	 * Converts an ipv4 string (blockA.blockB.blockC.blockD) to an unique ipv4 long
	 * value
	 * 
	 * @param ipString
	 * @return ipNumber
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static long getIpNumber(String ipString) throws NumberFormatException, Ipv4ConverterException {
		return Ipv4Converter.getIpBlock('A', ipString) + Ipv4Converter.getIpBlock('B', ipString)
				+ Ipv4Converter.getIpBlock('C', ipString) + Ipv4Converter.getIpBlock('D', ipString);
	};

	/**
	 * 
	 * @param position valid chars are: a|A|b|B|c|C|d|D
	 * @param ipNumber
	 * @return ipBlock number
	 * @throws Exception
	 * Example: getIpBlock('d',255) returns 255 while getIpBlock('a',4278190080) returns 255
	 */
	public static long getIpBlock(char position, long ipNumber) throws Ipv4ConverterException {
		checkBlockValue(ipNumber, position);
		switch (position) {
		case 'a':
		case 'A':
			return (ipNumber / blockB) % blockD;
		case 'b':
		case 'B':
			return (ipNumber / blockC) % blockD;
		case 'c':
		case 'C':
			return (ipNumber / blockD) % blockD;
		case 'd':
		case 'D':
			return ipNumber % blockD;
		default:
			throw new Ipv4ConverterException(String.format("getIpBlock(%c,%d) wrong position:%c", position, ipNumber, position));
		}
	};

	/**
	 * 
	 * @param position valid chars are: a|A|b|B|c|C|d|D
	 * @param ipString
	 * @return ipBlock number
	 * @throws NumberFormatException
	 * @throws Exception
	 * Example: getIpBlock('d',"0.0.0.255") returns 255 while getIpBlock('a',"225.0.0.0") returns 4278190080
	 */
	public static long getIpBlock(char position, String ipString) throws NumberFormatException, Ipv4ConverterException {
		long value;
		String[] ipStringSplitted = ipString.split("\\.");
		switch (position) {
		case 'a':
		case 'A':
			value = Long.valueOf(ipStringSplitted[0]);
			Ipv4Converter.checkBlockValue(value, position, ipString, blockD);
			return (value * blockB);
		case 'b':
		case 'B':
			value = Long.valueOf(ipStringSplitted[1]);
			Ipv4Converter.checkBlockValue(value, position, ipString, blockD);
			return (value * blockC);
		case 'c':
		case 'C':
			value = Long.valueOf(ipStringSplitted[2]);
			Ipv4Converter.checkBlockValue(value, position, ipString, blockD);
			return (value * blockD);
		case 'd':
		case 'D':
			value = Long.valueOf(ipStringSplitted[3]);
			Ipv4Converter.checkBlockValue(value, position, ipString, blockD);
			return value;
		default:
			throw new Ipv4ConverterException(String.format("getIpBlock(%c,%s) wrong position:%c", position, ipString, position));
		}
	};
	
	public static void checkBlockValue(long value, char position, String ipString, long max) throws Ipv4ConverterException {
		if (value >= blockD)
			throw new Ipv4ConverterException(String.format("getIpBlock(%c,%d) block value %d greater than %c", position,
					ipString, value, blockD));
	};
	
	public static void checkBlockValue(long ipNumber, char position) throws Ipv4ConverterException {
		if (ipNumber < 0 || ipNumber > (blockA - 1)) {
			throw new Ipv4ConverterException(
					String.format("getIpBlock(%c,%d) is not in the range 1:%d", position, ipNumber, (blockA - 1)));
		}
	};
	
	public static Map<Long, String> generateHashMap(long initialIpNumber, long endIpNumber) throws Ipv4ConverterException {
		Map<Long, String> map = new ConcurrentHashMap<>();
		for (Long x = initialIpNumber; x < (initialIpNumber + endIpNumber); x++) {
			map.put(x, Ipv4Converter.getIpString(x));
		}
		return map;
	};
	
	public static Map<Long, String> generateHashMap(String initialIpString, String endIpString) throws Ipv4ConverterException {
		return Ipv4Converter.generateHashMap(Ipv4Converter.getIpNumber(initialIpString), Ipv4Converter.getIpNumber(endIpString));
	};

}


