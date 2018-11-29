

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Panel_3Test.
 *
 * @Zhenjie Jiang, Tao Lin, Yilei Liang,  Bonian Hu
 * @1.0
 */
public class Panel_3Test
{
    /**
     * Default constructor for test class Panel_3Test
     */
    public Panel_3Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    /**
     * test if returns the correct average number of reviews
     */
    @Test
    public void testAvgNumberOfReviews()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "400");
     assertEquals(12,panel3.getAvgNumberOfReviews());
    }
    
    /**
     * test if returns the correct number of properties
     */
    @Test
    public void tesNumberOfProperties()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "200");
     assertEquals(49508,panel3.getNumberOfProperties());
        }
        
    /**
     * test if returns the correct number of entire house/apartment
     */    
    @Test
    public void testgetNumApart()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "100");
     assertEquals(10764,panel3.getNumApart());
        }
        
    /**
     * test if return the correct pricest neighbourhood
     */    
    @Test
    public void testexpensiveNeigh()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "50");
     assertEquals("Westminster",panel3.expensiveNeigh());
        }
        
    /**
     * test if return the correct number of 
     * properties where the availability is long-term( more than 90 days)
     */    
    @Test
    public void testlongTermNum()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "200");
     assertEquals(25312,panel3.longTermNum());
        }   
        
    /**
     * test if return the correct the cheapest properties 
     */    
    @Test
    public void testcheapProp()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("200", "400");
     System.out.println(panel3.cheapProp());
     assertEquals("SPACIOUS 1-bed 1-bath flat in fantastic location",panel3.cheapProp());
        }   
    
    /**
     * test if return the correct property with the most review
     */    
    @Test
    public void testmostReviewed()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "200");
     assertEquals("SOHO W1/2 THEATRELAND DESIGNER FLAT",panel3.mostReviewed());
        }
        
    /**
     * test if return the correct neighbourhood with the most properties
     */    
    @Test
    public void testmostProp()
    {Panel_3 panel3 = new Panel_3();
     panel3.setMinMax("0", "200");
     assertEquals("Tower Hamlets",panel3.getMostProp());
        }    
}
