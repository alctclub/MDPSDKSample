package com.alct.mdpsdksample.demo;


import com.alct.mdp.model.EnterpriseIdentity;
import com.alct.mdp.model.MultiIdentity;

import java.util.List;

public class MockIdentity {

    public static MultiIdentity initMultiEnterprise(List<EnterpriseIdentity> multiEnterprise, String driverIdentity) {
        MultiIdentity identity = new MultiIdentity();
        identity.setEnterpriseIdentities(multiEnterprise);
        identity.setDriverIdentity(driverIdentity);

        return identity;
    }
}