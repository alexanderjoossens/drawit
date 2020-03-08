package drawit;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   DoublePointTest.class,
   DoubleVectorTest.class,
   IntPointTest.class,
   IntVectorTest.class,
   PointArraysTest.class,
   RoundedPolygonTest.class,
})
public class TestSuite {

}
