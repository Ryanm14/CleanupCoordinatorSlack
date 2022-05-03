package controller.actions;

import backend.DataRepositoryInterface;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ActionRunnerTest {

    @Test
    public void test() {
        ActionRunner.Action mockedAction = mock(ActionRunner.Action.class);

        DataRepositoryInterface dataRepo = new FakeDataRepository();

        ActionRunner runner = new ActionRunner(dataRepo, null);
        runner.runAction(mockedAction);

        verify(mockedAction).run(dataRepo, null);
    }

}