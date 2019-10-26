package com.homeworkreminder.entity;

public class Count {
    /**
     * code : 200
     * message : ok
     * data : {"done":3,"undo":1}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * done : 3
         * undo : 1
         */

        private int done;
        private int undo;

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public int getUndo() {
            return undo;
        }

        public void setUndo(int undo) {
            this.undo = undo;
        }
    }
}
