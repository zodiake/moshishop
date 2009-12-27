import helper.CategoryTreeElement;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {


    @Before
    public void setUp() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }


    @Test
    public void createAndRetrieveCategory() {
        // Create a new category and save it
        Category rootCategory = new Category();
        rootCategory.name = "Phones";
        rootCategory.description = "Latest phones with low prices";
        rootCategory.headline = "Buy phones!";
        rootCategory.save();

        Category childCategory = new Category();
        childCategory.name = "GSM Phone";
        childCategory.description = "3g is standard";
        childCategory.headline = "Great TE2";
        childCategory.parent = rootCategory;
        childCategory.save();

        // Retrieve the category and check it's parent as well
        Category categoryRetrieved = Category.find("byName", "GSM Phone").first();

        // Test
        assertNotNull(categoryRetrieved);
        assertEquals("GSM Phone", categoryRetrieved.name);
        assertEquals("Phones", categoryRetrieved.parent.name);

        // Check data.yml is loaded as well:
        Category categoryRetrievedFromDataFile = Category.find("byName", "Science").first();
        assertEquals("Books", categoryRetrievedFromDataFile.parent.name);
    }

    @Test
    public void fetchingCategoryTree() {
        CategoryTreeElement symbolicCategory = CategoryTreeElement.fetchTreeElement(null);
        assertNotNull(symbolicCategory);

        //get master active roots (in our test it has a value of 1)
        assertEquals(1, symbolicCategory.childs.size());

        //there should be two sub cats with correct order
        CategoryTreeElement books = symbolicCategory.childs.get(0);
        assertEquals(2, books.childs.size());

        //elements of books
        assertEquals("Science", books.childs.get(0).getName());
        assertEquals("Math", books.childs.get(1).getName());
    }


}
