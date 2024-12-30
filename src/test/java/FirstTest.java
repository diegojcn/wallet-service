import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FirstTest {

    @Test
    void singleTest(){
        assertThat(1).isEqualTo(1);
    }
}
