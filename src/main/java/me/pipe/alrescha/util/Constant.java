package me.pipe.alrescha.util;

public class Constant {
    /**
     * user account status
     */
    public enum AccountStatusType {
        /**
         * 禁用
         */
        Disable(0),
        /**
         * 正常
         */
        NORMAL(1);

        private Integer value;

        AccountStatusType(Integer value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
