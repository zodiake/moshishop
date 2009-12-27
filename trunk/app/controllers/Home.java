package controllers;

import helper.CategoryTreeElement;
import play.mvc.*;

public class Home extends Controller {

    public static void index() {
        category(null);
    }

    public static void category(Long categoryId) {
        CategoryTreeElement menu = CategoryTreeElement.fetchTreeElement(null);
        render(menu);
    }


}