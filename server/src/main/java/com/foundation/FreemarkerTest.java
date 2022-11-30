package com.foundation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.mail.*;
import javax.mail.internet.*;

public class FreemarkerTest {

    private static final String TEMPLATE_PATH = "E:/需求/邮件正文模板/template/互换模板/";
    private static final String OUTPUT_PATH = "E:/需求/邮件正文模板/template/互换模板/eml/";
    private static final String TEMPLATE_NAME = "邮件发送模板-结算通知书正文-互换-20220808";


    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

        try(StringWriter out = new StringWriter()) {
            // step1 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            configuration.setDefaultEncoding("UTF-8");
            // step2 创建数据模型
            Map<String, Object> dataMap = generateData();
            // step3 加载模版文件
            Template template = configuration.getTemplate(TEMPLATE_NAME + ".html");
            // step4 渲染
            template.process(dataMap, out);
            generateEmail(out.toString());
            System.out.println(">>>>>>>>文件创建成功" + LocalDate.now().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> generateData() {
        return TEMPLATE_NAME.contains("期权") ? generateOptionData() : generateSwapData();
    }

    private static Map<String, Object> generateOptionData() {
        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String, Object>> emailDTOs = new ArrayList<>();

        Map<String, Object> emailDTO = new HashMap<>();
        Map<String, Object> tradeDTO = new HashMap<>();

        Map<String, Object> product = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        products.add(product);
        product.put("initialDate", LocalDate.of(2022, 6,13));
        product.put("settlementDate", LocalDate.of(2022, 6,14));

        tradeDTO.put("counterpartyName", "平安银行");
        tradeDTO.put("department", "资管");
        tradeDTO.put("displayTradeCurrency", "HKD");
        tradeDTO.put("displaySettlementCurrency", "CNY");
        tradeDTO.put("crossBorder", "compo1");
        tradeDTO.put("notional", new BigDecimal("100"));
        tradeDTO.put("initialXRate", new BigDecimal("1.0000"));
        tradeDTO.put("tradeConfirmationId", "211206100440_1");
        tradeDTO.put("products", products);

        Map<String, Object> eventDTO = new HashMap<>();
        eventDTO.put("eventType", LCMEventTypeEnum.UNWIND);
        eventDTO.put("eventId", "1467762486362124336");
        eventDTO.put("unwindConfirmationId", "SWHY-2422003-22002-01");
        eventDTO.put("unwindDate", LocalDate.of(2022, 6,16));
        eventDTO.put("paymentDate", LocalDate.of(2022, 6,15));
        eventDTO.put("paymentMethod", "DIRECT_PAY");
        eventDTO.put("settleNotionalAmount", new BigDecimal("1000.123"));
        eventDTO.put("settlementAmount", new BigDecimal("-1000.123"));
        eventDTO.put("optionSettlementPaymentAmountOfTradeCurrency", new BigDecimal("1000.123"));
        eventDTO.put("optionSettlementPaymentAmount", new BigDecimal("1000.123"));
        eventDTO.put("finalPremium", new BigDecimal("1000.123"));
        eventDTO.put("finalCoupon", new BigDecimal("1000.123"));
        eventDTO.put("prePayPremium", new BigDecimal("1"));
        eventDTO.put("settlementXRate", new BigDecimal("1"));

        emailDTO.put("tradeDTO", tradeDTO);
        emailDTO.put("eventDTO", eventDTO);

        emailDTOs.add(emailDTO);
        dataMap.put("emailDTOs", emailDTOs);
        dataMap.put("today", "2022-06-14");
        dataMap.put("sendTime", "2022");
        dataMap.put("emailSendSucceed", true);

        return dataMap;
    }

    private static Map<String, Object> generateSwapData() {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> party = new HashMap<>();
        Map<String, Object> swapContract = new HashMap<>();
        Map<String, Object> swapSettle = new HashMap<>();
        Map<String, Object> trade = new HashMap<>();
        Map<String, Object> swapTrade = new HashMap<>();

        party.put("partyName", "千合资本-昀锦3号");
        party.put("departmentName", "私募证券投资基金");

        swapContract.put("tradeConfirmId", "SWHY-SGB-0417003");
        trade.put("productType", null);
//        trade.put("productType", "SINGLE_BULL_BEAR_INDEX_SWAP");
        trade.put("tradingCurrency", "HKD");
        trade.put("settleCurrency", "USD");
        trade.put("effectiveDate", LocalDate.of(2022, 6,17));
        trade.put("initExchangeRate", new BigDecimal("1.1234"));

        swapTrade.put("swapInterestDirection", SwapDirection.PAY);

        swapSettle.put("lcmEventType", "到期结算");
        swapSettle.put("lcmNoticeBookNum", "SWHY-SGB-0417003-01");
        swapSettle.put("availableDate1", LocalDate.of(2022, 6,18));
        swapSettle.put("paymentDate", LocalDate.of(2022, 6,19));
        swapSettle.put("paymentMethod", "直接支付");
        swapSettle.put("beforeSettleNotionalAmount", new BigDecimal("1300"));
        swapSettle.put("settleNotionalAmount", new BigDecimal("1000"));
        swapSettle.put("afterSettleNotionalAmount", new BigDecimal("300"));
        swapSettle.put("settleExchangeRate", new BigDecimal("2.345"));
        swapSettle.put("realizeTotalEquityIncome", new BigDecimal("789.231"));
        swapSettle.put("settleRealizeTotalEquityIncome", new BigDecimal("789.231"));
        swapSettle.put("realizeInterestIncome", new BigDecimal("237.382"));
        swapSettle.put("settleRealizeInterestIncome", new BigDecimal("237.382"));
        swapSettle.put("compoundSettlementAmount", new BigDecimal("-1000"));

        dataMap.put("party", party);
        dataMap.put("swapContract", swapContract);
        dataMap.put("swapSettle", swapSettle);
        dataMap.put("swapTrade", swapTrade);
        swapContract.put("trade", trade);
        Map<String, Object> emailDTOs = new HashMap<>();
        emailDTOs.put("emailDTOs", dataMap);
        return emailDTOs;
    }


    public static void generateEmail(String emailContent) {
        try(FileOutputStream out = new FileOutputStream(new File(OUTPUT_PATH, TEMPLATE_NAME + ".eml"))) {
            String subject = emailContent.substring(emailContent.indexOf("<title>") + 7, emailContent.indexOf("</title>"));
            Session mailSession = Session.getDefaultInstance(new Properties());
            MimeMessage email =  new  MimeMessage(mailSession);
            email.setSentDate(new Date());
            email.setSubject(subject.trim(), "UTF-8");
            email.setContent(emailContent,  "text/html;charset=UTF-8" );
            email.writeTo(out);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
