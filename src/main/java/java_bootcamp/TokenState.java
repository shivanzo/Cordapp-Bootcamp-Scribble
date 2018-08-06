package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/kotlin/examples/IAmAState.java and
 * src/main/kotlin/examples/IAmAlsoAState.java for examples. */
public class TokenState implements ContractState{

    private Party issuer;
    private Party owner;
    private int amount;

    public TokenState(Party issuer,Party owner,int amount)
    {
        this.issuer = issuer;
        this.owner = owner;
        this.amount = amount;
    }

    public Party getIssuer()
    {
        return issuer;
    }

    public Party getOwner()
    {
        return owner;
    }

    public int getAmount()
    {
        return amount;
    }


    public List<AbstractParty> getParticipants() {
        List<AbstractParty> participants = new ArrayList<>();
        participants.add(issuer);
        participants.add(owner);
        return  participants;
    }
}