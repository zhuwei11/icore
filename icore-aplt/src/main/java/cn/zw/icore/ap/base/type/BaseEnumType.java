package cn.zw.icore.ap.base.type;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 公用枚举类定义
 */
public interface BaseEnumType {
    public static enum E_YESORNO{
        YES("YES","Y","YES","YES"),
        NO("NO","N","NO","NO");
        private String id;
        @EnumValue
        private String value;
        private String longname;
        private String desc;

        private E_YESORNO(String id, String value, String longname, String desc) {
            this.id = id;
            this.value = value;
            this.longname = longname;
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public String getLongname() {
            return longname;
        }

        public String getDesc() {
            return desc;
        }

        public String toString(){
            return String.valueOf(this.value);
        }
    }

    public static enum E_SEQBUILDCLASS{
        /**
         * A - identification section
         * 标识
         */
        RECOGNITION("RECOGNITION","A","identification section","identification section"),
        /**
         * B - serial number
         * 顺序号
         */
        SEQUENCE("SEQUENCE","B","serial number","serial number"),
        /**
         * C - check bit
         * 校验位
         */
        CHECKBIT("CHECKBIT","C","check bit","check bit"),
        /**
         * S - constants
         * 常量
         */
        CONSTANTS("CONSTANTS","S","constants","constants");
        private String id;
        @EnumValue
        private String value;
        private String longname;
        private String desc;

        E_SEQBUILDCLASS(String id, String value, String longname, String desc) {
            this.id = id;
            this.value = value;
            this.longname = longname;
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public String getLongname() {
            return longname;
        }

        public String getDesc() {
            return desc;
        }

        public String toString(){
            return String.valueOf(this.value);
        }
    }

    public static enum E_PADDINGMODE{
        /**
         * L - left padding
         * 左补齐
         */
        LEFT("LEFT","L","left padding","left padding"),
        /**
         * R - right padding
         * 右补齐
         */
        RIGHT("RIGHT","R","right padding","right padding");
        private String id;
        @EnumValue
        private String value;
        private String longname;
        private String desc;

        E_PADDINGMODE(String id, String value, String longname, String desc) {
            this.id = id;
            this.value = value;
            this.longname = longname;
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public String getLongname() {
            return longname;
        }

        public String getDesc() {
            return desc;
        }

        public String toString(){
            return String.valueOf(this.value);
        }
    }
}
