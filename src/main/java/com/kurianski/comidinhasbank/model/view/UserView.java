package com.kurianski.comidinhasbank.model.view;

public class UserView {
    public interface Simple {}
    public interface Detailed extends Simple {}
    public interface Confidential extends Detailed {}
}
