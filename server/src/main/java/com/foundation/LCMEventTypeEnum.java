package com.foundation;


public enum LCMEventTypeEnum {
    OPEN("开仓"),
    ROLL("展期"),
    AMEND("更新"),
    SETTLE("结算"),
    UNWIND("平仓"),
    UNWIND_PARTIAL("部分平仓"),
    EXPIRATION("到期"),
    EXTENSION("展期"),
    COUPON("派息"),
    EXERCISE("行权"),
    EXERCISE_PARTIAL("部分行权"),
    KNOCK_OUT("敲出"),
    KNOCK_IN("敲入"),
    DIVIDEND("分红"),
    PAYMENT("支付"),
    OBSERVE("观察"),
    SNOW_BALL_EXERCISE("雪球到期行权"),
    PHOENIX_EXERCISE("凤凰到期行权"),
    PRE_PAYMENT("支付前端费用"),
    DELETE("删除交易"),
    DIVIDEND_PAYMENT("派息支付"),
    MARGIN("保证金"),
    INTEREST_PAYMENT("期间收支"),
    EXPAND_SIZE("扩大规模"),
    TRANSFER_POSITION("调仓"),
    OTHERS("其他");

    private final String description;

    public String getDescription() {
        return this.description;
    }

    private LCMEventTypeEnum(final String description) {
        this.description = description;
    }
}
