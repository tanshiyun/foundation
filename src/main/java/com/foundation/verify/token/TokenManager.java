package com.foundation.verify.token;

import java.util.Random;

/**
 * @ClassName TokenManager
 * @Description token管理类
 * @Author tsy20
 * @Date 2019/12/28
 **/
public class TokenManager {

    private int tokenLength; //随机码长度
    private boolean ignoreCase; //是否忽略大小写标志
    private Random random = new Random();

    public TokenManager() {
        this.tokenLength = 6; //token默认长度设置为6
        this.ignoreCase = true; //默认校验时忽略大小写
    }

    /**
     * 根据默认长度生成随机大小写字母序列
     * @return sequence
     */
    public String generateRandomAlphabet(){

        StringBuilder sequence = new StringBuilder();

        for(int i=0; i<this.tokenLength; i++){

            //大小写随机标识
            int caseFlag = random.nextInt(2);

            if(caseFlag > 0){
                sequence.append(getRandomLower());
            }else {
                sequence.append(getRandomUpper());
            }
        }

        return sequence.toString();
    }



    /**
     * 生成指定长度随机大小写字母序列
     * @return sequence
     */
    public String generateRandomAlphabet(int length){

        StringBuilder sequence = new StringBuilder();

        for(int i=0; i<length; i++){

            //大小写随机标识
            int caseFlag = random.nextInt(2);

            if(caseFlag > 0){
                sequence.append(getRandomLower());
            }else {
                sequence.append(getRandomUpper());
            }
        }

        return sequence.toString();
    }


    /**
     * 生成默认长度数字序列
     */
    public String generateRandomNum(){

        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < this.tokenLength; i++) {
            sequence.append(getRandomNum());
        }

        return sequence.toString();
    }



    /**
     * 生成指定长度数字序列
     */
    public String generateRandomNum(int length){

        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sequence.append(getRandomNum());
        }

        return sequence.toString();
    }



    /**
     * 生成默认长度大小写字母+数字混合序列
     */
    public String generateMultiSeq(){
        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < this.tokenLength; i++) {
            getRandomCharacter(sequence);
        }

        return sequence.toString();
    }


    /**
     * 生成指定长度大小写字母+数字混合序列
     */
    public String generateMultiSeq(int length){
        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < length; i++) {
            getRandomCharacter(sequence);
        }

        return sequence.toString();
    }


    /**
     * 随机生成大小写字母或数字
     * @param sequence
     */
    private void getRandomCharacter(StringBuilder sequence) {

        int typeFlag = random.nextInt(2);
        if (1>typeFlag){
            sequence.append(getRandomNum());
        }else{
            int caseFlag = random.nextInt(2);
            if(1>caseFlag){
                sequence.append(getRandomUpper());
            }else{
                sequence.append(getRandomLower());
            }
        }
    }


    /**
     * 返回随机0-9整数
     * @return
     */
    public String getRandomNum(){
        return String.valueOf(random.nextInt(10));
    }



    /**
     * 返回随机大写字母
     * @return
     */
    public String getRandomUpper(){
        char u = (char) (random.nextInt(26)+65); //大写字母ascii范围65-90
        return String.valueOf(u);
    }



    /**
     * 返回随机小写字母
     * @return
     */
    public String getRandomLower(){
        char l = (char) (random.nextInt(26)+97); //小写字母ascii范围97-122
        return String.valueOf(l);
    }





    public int getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(int tokenLength) {
        this.tokenLength = tokenLength;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

}
