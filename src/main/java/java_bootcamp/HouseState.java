package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Define your state object here.
 */
public class HouseState implements ContractState
{
    private String address;
    private Party Owner;

    public HouseState(String address, Party owner)
    {
        this.address = address;
        Owner = owner;
    }

    public Party getOwner() {
        return Owner;
    }

    public String getAddress() {
        return address;
    }

    public static void main(String[] args)
    {
        Party joel = null;
        HouseState state = new HouseState("Golden Heritage Society Dhavli Ponda Goa",joel);

    }


    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(Owner);
    }
}