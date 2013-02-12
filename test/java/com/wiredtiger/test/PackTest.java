package com.wiredtiger.test;

import com.wiredtiger.db.PackOutputStream;
import com.wiredtiger.db.PackInputStream;
import com.wiredtiger.db.WiredTigerPackingException;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class PackTest {

    // Some random numbers for testing.
    static long[] testers = {
        -12, -145, -14135, -1352308572, -1, 0, 1, 12, 145, 12314,
        873593485, -30194371, -4578928, 75452136, -28619244, 93580892,
        83350219, 27407091, -82413912, -727169, -3748613, 54046160,
        -49539872, -4517158, 20397230, -68522195, 61663315, -6009306,
        -57778143, -97631892, -62388819, 23581637, 2417807, -17761744,
        -4174142, 92685293, 84597598, -83143925, 95302021, 90888796,
        88697909, -89601258, 93585507, 63705051, 51191732, 60900034,
        -93016118, -68693051, -49366599, -90203871, 58404039, -79195628,
        -98043222, 35325799, 47942822, 11582824, 93322027, 71799760,
        65114434, 42851643, 69146495, -86855643, 40073283, 1956899,
        28090147, 71274080, -95192279, -30641467, -1142067, -32599515,
        92478069, -90277478, -39370258, -77673053, 82435569, 88167012,
        -39048877, 96895962, -8587864, -70095341, 49508886, 69912387,
        24311011, -58758419, 63228782, -52050021, 24687766, 34342885,
        97830395, 74658034, -9715954, -76120311, -63117710, -19312535,
        42829521, 32389638, -51273506, 16329653, -39061706, -9931233,
        42174615, 75412082, -26236331, 57741055, -17577762, 3605997,
        -73993355, -54545904, -86580638, 84432898, -83573465, -1278,
        636, -9935, 9847, 8300, -5170, -2501, 6031, -6658, -9780, -5351,
        6573, -5582, -1994, -7498, -5190, 7710, -8125, -6478, 3670, 4293,
        1903, 2367, 3501, 841, -1718, -2303, -670, 9668, 8391, 3719, 1453,
        7203, -9693, 1294, -3549, -8941, -5455, 30, 2773, 8354, 7272,
        -9794, -4806, -7091, -8404, 8297, -4093, -9890, -4948, -38, -66,
        -12, 9, 50, -26, 4, -25, 62, 2, 47, -40, -22, -87, 75, -43, -51,
        65, 7, -17, -90, -27, 56, -60, 27, -2, 2, -3, 4, 7, 8, -8
    };

    @Test
    public void pack01()
    throws WiredTigerPackingException {
        String format = "b";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldByte((byte)8);

        Assert.assertEquals(format, packer.getFormat());
        byte[] packed = packer.getValue();

        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertEquals(unpacker.getFieldByte(), (byte)8);
    }

    @Test
    public void pack02()
    throws WiredTigerPackingException {
        String format = "biqrhS";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldByte((byte)8);
        packer.addFieldInt(124);
        packer.addFieldLong(1240978);
        packer.addFieldRecord(5680234);
        packer.addFieldShort((short)8576);
        packer.addFieldString("Hello string");

        Assert.assertEquals(format, packer.getFormat());
        byte[] packed = packer.getValue();

        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertEquals(unpacker.getFieldByte(), (byte)8);
        Assert.assertEquals(unpacker.getFieldInt(), 124);
        Assert.assertEquals(unpacker.getFieldLong(), 1240978);
        Assert.assertEquals(unpacker.getFieldRecord(), 5680234);
        Assert.assertEquals(unpacker.getFieldShort(), 8576);
        Assert.assertEquals(unpacker.getFieldString(), "Hello string");
    }

    @Test
    public void pack03()
    throws WiredTigerPackingException {
        String format = "SS";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldString("Hello 1");
        packer.addFieldString("Hello 2");

        byte[] packed = packer.getValue();
        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertEquals(unpacker.getFieldString(), "Hello 1");
        Assert.assertEquals(unpacker.getFieldString(), "Hello 2");
    }

    @Test
    public void pack04()
    throws WiredTigerPackingException {
        String format = "U";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldByteArray("Hello 1".getBytes());

        byte[] packed = packer.getValue();
        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertTrue(java.util.Arrays.equals(
                              unpacker.getFieldByteArray(), "Hello 1".getBytes()));
    }

    @Test
    public void pack05()
    throws WiredTigerPackingException {
        String format = "uuu";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldByteArray("Hello 1".getBytes());
        packer.addFieldByteArray("Hello 2".getBytes());
        packer.addFieldByteArray("Hello 3".getBytes());

        byte[] packed = packer.getValue();
        //printByteArray(packed, packed.length);
        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertTrue(java.util.Arrays.equals(
                              unpacker.getFieldByteArray(), "Hello 1".getBytes()));
        Assert.assertTrue(java.util.Arrays.equals(
                              unpacker.getFieldByteArray(), "Hello 2".getBytes()));
        Assert.assertTrue(java.util.Arrays.equals(
                              unpacker.getFieldByteArray(), "Hello 3".getBytes()));
    }

    @Test
    public void pack06()
    throws WiredTigerPackingException {
        String format = "uiS";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldByteArray("Hello 1".getBytes());
        packer.addFieldInt(12);
        packer.addFieldString("Hello 3");

        byte[] packed = packer.getValue();
        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertTrue(java.util.Arrays.equals(
                              unpacker.getFieldByteArray(), "Hello 1".getBytes()));
        Assert.assertEquals(unpacker.getFieldInt(), 12);
        Assert.assertEquals(unpacker.getFieldString(), "Hello 3");
    }

    @Test
    public void pack07()
    throws WiredTigerPackingException {
        String format = "4s";
        PackOutputStream packer = new PackOutputStream(format);
        packer.addFieldString("Hello 1");

        byte[] packed = packer.getValue();
        PackInputStream unpacker = new PackInputStream(format, packed);
        Assert.assertEquals(unpacker.getFieldString(), "Hell");
    }

    @Test
    public void packUnpackNumber01()
    throws WiredTigerPackingException {
        // Verify that we can pack and unpack single signed longs.
        for (long i:testers) {
            PackOutputStream packer = new PackOutputStream("Q");
            packer.addFieldLong(i);
            PackInputStream unpacker =
                new PackInputStream("Q", packer.getValue());
            long unpacked = unpacker.getFieldLong();
            if (i != unpacked)
                System.out.println(
                    i + " did not match " + unpacked);
        }
    }

    @Test
    public void packUnpackNumber02()
    throws WiredTigerPackingException {
        // Verify that we can pack and unpack pairs of signed longs reliably.
        // This is interesting because it ensures that we are tracking the
        // number of bytes used by number packing correctly.
        for (int i = 0; i + 1 < testers.length; i += 2) {
            long val1 = testers[i];
            long val2 = testers[i+1];

            PackOutputStream packer = new PackOutputStream("QQ");
            packer.addFieldLong(val1);
            packer.addFieldLong(val2);
            PackInputStream unpacker =
                new PackInputStream("QQ", packer.getValue());
            long unpacked = unpacker.getFieldLong();
            if (val1 != unpacked)
                System.out.println(i + " did not match " + unpacked);
            unpacked = unpacker.getFieldLong();
            if (val2 != unpacked)
                System.out.println(i + " did not match " + unpacked);
        }
    }

    // A debug helper method
    private void printByteArray(byte[] bytes, int len) {
        for (int i = 0; i < len; i++) {
            System.out.println(String.format(
                                   "\t%8s", Integer.toBinaryString(
                                       bytes[i] & 0xff)).replace(' ', '0'));
        }
    }

    public static void main(String[] args) {
        PackTest tester = new PackTest();
        try {
            tester.pack01();
            tester.pack02();
            tester.packUnpackNumber01();
        } catch (WiredTigerPackingException wtpe) {
            System.err.println("Packing exception: " + wtpe);
        }
    }
}
