import java.util.HashMap;
import org.junit.jupiter.api.Test;
public class ExperimentSecure {


    public static void main(String[] args) {
//        substitutionEncode();
//        caesarEncode();
//        virginiaEncode();
    }


    // 维吉尼亚密码解密
    @Test
    public void virginiaEncode() {

        // 初始化 map 集合
        HashMap<String, String> map = new HashMap<>();

        map.put("a", "0");		map.put("A", "26");		map.put("b", "1");		map.put("B", "27");		map.put("c", "2");
        map.put("C", "28");		map.put("d", "3");		map.put("D", "29");		map.put("e", "4");		map.put("E", "30");
        map.put("f", "5");		map.put("F", "31");		map.put("g", "6");		map.put("G", "32");		map.put("h", "7");
        map.put("H", "33");		map.put("i", "8");		map.put("I", "34");		map.put("j", "9");		map.put("J", "35");
        map.put("k", "10");		map.put("K", "36");		map.put("l", "11");		map.put("L", "37");		map.put("m", "12");
        map.put("M", "38");		map.put("n", "13");		map.put("N", "39");		map.put("o", "14");		map.put("O", "40");
        map.put("p", "15");		map.put("P", "41");		map.put("q", "16");		map.put("Q", "42");		map.put("r", "17");
        map.put("R", "43");		map.put("s", "18");		map.put("S", "44");		map.put("t", "19");		map.put("T", "45");
        map.put("u", "20");		map.put("U", "46");		map.put("v", "21");		map.put("V", "47");		map.put("w", "22");
        map.put("W", "48");		map.put("x", "23");		map.put("X", "49");		map.put("y", "24");		map.put("Y", "50");
        map.put("z", "25");		map.put("Z", "51");		map.put("0", "a");		map.put("1", "b");		map.put("2", "c");
        map.put("3", "d");		map.put("4", "e");		map.put("5", "f");		map.put("6", "g");		map.put("7", "h");
        map.put("8", "i");		map.put("9", "j");		map.put("10", "k");		map.put("11", "l");		map.put("12", "m");
        map.put("13", "n");		map.put("14", "o");		map.put("15", "p");		map.put("16", "q");		map.put("17", "r");
        map.put("18", "s");		map.put("19", "t");		map.put("20", "u");		map.put("21", "v");		map.put("22", "w");
        map.put("23", "x");		map.put("24", "y");		map.put("25", "z");		map.put("26", "A");		map.put("27", "B");
        map.put("28", "C");		map.put("29", "D");		map.put("30", "E");		map.put("31", "F");		map.put("32", "G");
        map.put("33", "H");		map.put("34", "I");		map.put("35", "J");		map.put("36", "K");		map.put("37", "L");
        map.put("38", "M");		map.put("39", "N");		map.put("40", "O");		map.put("41", "P");		map.put("42", "Q");
        map.put("43", "R");		map.put("44", "S");		map.put("45", "T");		map.put("46", "U");		map.put("47", "V");
        map.put("48", "W");		map.put("49", "X");		map.put("50", "Y");		map.put("51", "Z");


        // 密钥
        String secret = "cipher";
        // 密文
        String secretText = "cbihgbdmvprjcbupzv";

        // 解密方法
        char[] arr = new char[secretText.length()];// 创建数组来装密文

        char[] secretTextArr = secretText.toCharArray();

        char[] secretArr = secret.toCharArray();

        int j = 0;

        for (int i = 0; i < secretTextArr.length; i++) {
            if ((secretTextArr[i]) == ' ') {

                arr[i] = ' '; // 空格填充

            } else {

                String s2 = String.valueOf(secretTextArr[i]);
                int mw = Integer.parseInt(map.get(s2));// 强转为int

                String s3 = String.valueOf(secretArr[j % (secretArr.length)]);
                int my = Integer.parseInt(map.get(s3));// 强转为int
                int demo1=0;
                if(mw>25){
                    demo1 = (mw - my + 26) % 26+26;
                }else{
                    demo1 = (mw - my + 26) % 26;
                }
                j++;
                String s = String.valueOf(demo1);
                char a1 = (map.get(s)).charAt(0);// 把拿到的字符转化为char类型
                arr[i] = a1;// 经过循环把密文依次放进数组中

            }
        }
        System.out.print("解密后明文是：");
        System.out.println(arr);
    }


    // 置换密码
    @Test
    public void substitutionEncode(){
        String secret = "2143";
        String plainText = "abcdefgh";
        int row = (int) Math.ceil(plainText.length() * 1.0 / secret.length());
        int col = secret.length();


        char[] plainTextChars = plainText.toCharArray();
        char[] secretChars = secret.toCharArray();
        int[] secretInt = new int[col];

        for (int i = 0; i < col; i++) {
            secretInt[i] = Integer.parseInt(String.valueOf(secretChars[i]));
        }


        char[][] chars = new char[row][col];
        int k = 0;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (k > plainTextChars.length - 1){
                    break;
                }
                chars[i][j] = plainTextChars[k];
                k++;
            }
        }

        for (int i = 0; i < col; i++){
            for (int j = 0; j < row; j++) {
                char c = chars[j][secretInt[i] - 1];
                if ( c == '\0'){
                    break;
                }
                System.out.print(c);
            }
        }
    }


    // 凯撒密码加密
    @Test
    public void caesarEncode(){

        String plainText = "hello world";
        int secretKey = 4;

        char[] chars = plainText.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            // 小写字母
            if (chars[i] >= 'a' && chars[i] <= 'z'){

                // 判断是否越界
                if (chars[i] + secretKey > 122){
                    chars[i] = (char) (chars[i] + secretKey - 26);
                }
                else {
                    chars[i] = (char) (chars[i] + secretKey);
                }

            }
            // 大写字母
            else {
                // 是否越界
                if (chars[i] + secretKey > 90){
                    chars[i] = (char) (chars[i] + secretKey - 26);
                }
                else {
                    chars[i] = (char) (chars[i] + secretKey);
                }
            }

        }

        // 打印加密字符串
        System.out.println(new String(chars));
    }

}
