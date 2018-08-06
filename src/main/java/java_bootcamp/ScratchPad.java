package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.transactions.TransactionBuilder;

import java.security.PublicKey;
import java.util.List;

public class ScratchPad {

    public static void main(String[] args) {
        StateAndRef<ContainerState> inputState = null;
        HouseState outputState = new HouseState("Golden Heritage Society Dhavli Ponda",null);
        PublicKey requiredSign = outputState.getOwner().getOwningKey();
        List<PublicKey> requiredSigner = ImmutableList.of(requiredSign);

        TransactionBuilder builder = new TransactionBuilder();
        builder.addInputState(inputState).addOutputState(outputState,"java_bootcamp.HouseContract")
                .addCommand(new HouseContract.Register(),requiredSigner);



    }
}
