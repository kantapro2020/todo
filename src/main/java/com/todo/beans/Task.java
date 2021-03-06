package com.todo.beans;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class Task implements Serializable {
        private static final long serialVersionUID = -41353466463452342L;
        private int id;
        @NotEmpty
        @Size(max = 20)
        private String task_title;
        @Min(0)
        @Max(3)
        private int status;
        @Min(0)
        @Max(2)
        private int priority;
        private Date started_date;
        private Date predict_finished_date;
        @Size(max=50)
        private String explanation;
        @Min(1)
        private  int required_time;
        private int mini_project_id;

        public String getTask_title() {
            return task_title;
        }

        public void setTask_title(String task_title) {
            this.task_title = task_title;
        }
        /**
         * @return status
         */
        public int getStatus() {
            return status;
        }
        /**
         * @param status セットする status
         */
        public void setStatus(int status) {
            this.status = status;
        }
        /**
         * @return priority
         */
        public int getPriority() {
            return priority;
        }
        /**
         * @param priority セットする priority
         */
        public void setPriority(int priority) {
            this.priority = priority;
        }
        /**
         * @return started_date
         */
        public Date getStarted_date() {
            return started_date;
        }
        /**
         * @param started_date セットする started_date
         */
        public void setStarted_date(Date started_date) {
            this.started_date = started_date;
        }
        /**
         * @return predict_finished_date
         */
        public Date getPredict_finished_date() {
            return predict_finished_date;
        }
        /**
         * @param predict_finished_date セットする predict_finished_date
         */
        public void setPredict_finished_date(Date predict_finished_date) {
            this.predict_finished_date = predict_finished_date;
        }
        /**
         * @return explanation
         */
        public String getExplanation() {
            return explanation;
        }
        /**
         * @param explanation セットする explanation
         */
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
        /**
         * @return required_time
         */
        public int getRequired_time() {
            return required_time;
        }
        /**
         * @param required_time セットする required_time
         */
        public void setRequired_time(int required_time) {
            this.required_time = required_time;
        }
        /**
         * @return mini_project_id
         */
        public int getMini_project_id() {
            return mini_project_id;
        }
        /**
         * @param mini_project_id セットする mini_project_id
         */
        public void setMini_project_id(int mini_project_id) {
            this.mini_project_id = mini_project_id;
        }

        /**
         * @return id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id セットする id
         */
        public void setId(int id) {
            this.id = id;
        }
}
