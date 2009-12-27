package models;

import helper.CategoryTreeElement;
import play.cache.Cache;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.io.File;
import java.util.List;

/**
 * @author Kaan Yamanyar
 */
@Entity
public class Category extends Model {

    /**
     *
     */
    private static final long serialVersionUID = -2186618514538554366L;

    @ManyToOne
    public Category parent;

    @Required
    @MinSize(1)
    public String name;

    @MaxSize(250)
    public String headline;

    @MaxSize(2000)
    public String description;

    public boolean active;

    public Integer rank = 0;


    @Override
    public String toString() {
        return name;
    }


    public Category() {
    }

   
}
