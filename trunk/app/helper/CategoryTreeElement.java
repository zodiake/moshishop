package helper;

import exceptions.RecursiveCategoryException;
import models.Category;
import play.cache.Cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Dec 25, 2009
 *
 * @author Kaan Yamanyar
 */
public class CategoryTreeElement implements Serializable {
    private String name;
    private Long id;
    private CategoryTreeElement parent;


    public CategoryTreeElement(Category c, CategoryTreeElement parent) {
        if (c != null) {
            this.name = c.name;
            this.id = c.id;
            this.parent = parent;
        } else name = "ROOT";
    }

    public ArrayList<CategoryTreeElement> childs = new ArrayList<CategoryTreeElement>();

    public void addElement(CategoryTreeElement treeElement) {

        //prevent recursive referencing
        checkIfOneOfParentIsActualyThisElement(treeElement);

        childs.add(treeElement);
    }

    private void checkIfOneOfParentIsActualyThisElement(CategoryTreeElement treeElement) {
        if (this.equals(treeElement)) throw new RecursiveCategoryException();
        if (parent != null) {
            parent.checkIfOneOfParentIsActualyThisElement(treeElement);
        }
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public CategoryTreeElement getParent() {
        return parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryTreeElement that = (CategoryTreeElement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


     /**
     * Cached representation of category tree.
     *
     * @param parentCategory
     * @return
     */
    public static CategoryTreeElement fetchTreeElement(Category parentCategory) {
        String key;
        if (parentCategory == null)
            key = "root";
        else
            key = String.valueOf(parentCategory.getId());

        CategoryTreeElement categoryTreeElement = Cache.get(key, CategoryTreeElement.class);

        if (categoryTreeElement == null) {
            categoryTreeElement = fetchTreeElement(parentCategory, null);
            Cache.set(key, categoryTreeElement, "60mn");

        }

        return categoryTreeElement;
    }

    private static CategoryTreeElement fetchTreeElement(Category parentCategory, CategoryTreeElement parentElement) {
        CategoryTreeElement parent = new CategoryTreeElement(parentCategory, parentElement);
        List<Category> childElements;
        if (parentCategory == null) {
            childElements = Category.find("parent is null and active = true order by rank").fetch();
        } else {
            childElements = Category.find("parent = ? and active = true order by rank", parentCategory).fetch();
        }
        if (childElements.size() == 0) return parent;
        for (Category child : childElements) {
            CategoryTreeElement treeElement = fetchTreeElement(child, parent);
            parent.addElement(treeElement);
        }
        return parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CategoryTreeElement");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}


