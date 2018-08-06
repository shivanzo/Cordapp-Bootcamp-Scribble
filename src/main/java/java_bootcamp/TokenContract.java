package java_bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

/* Our contract, governing how our state will evolve over time.
 * See src/main/kotlin/examples/ExampleContract.java for an example. */
public class TokenContract implements Contract{
    public static String ID = "java_bootcamp.TokenContract";

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException
    {
        if(tx.getInputStates().size() != 0)
            throw new IllegalArgumentException("Transaction must have zero inputs.");

        if(tx.getOutputStates().size() !=1)
            throw new IllegalArgumentException("Requires atleast one output");

        if(tx.getCommands().size() !=1)
            throw new IllegalArgumentException("Transaction must have atleast one command");

        ContractState output = tx.getOutput(0);
        Command command = tx.getCommand(0);

        if(!(output instanceof TokenState))
            throw new IllegalArgumentException("Output must be a tokenstate");
        if(!(command.getValue() instanceof Issue))
            throw new IllegalArgumentException("Command must be Issue command");

        TokenState token = (TokenState) output;
        if(token.getAmount() < 0)
            throw new IllegalArgumentException("Amount should not be less than zero and should be positive");

        List<PublicKey> requiredSignatures = command.getSigners();
        Party issuer = token.getIssuer();
        PublicKey issuerKey = issuer.getOwningKey();

        if(!(requiredSignatures.contains(issuerKey)))
            throw new IllegalArgumentException("Issuer must be required signer.");



    }

    public static class Issue implements CommandData {}


}