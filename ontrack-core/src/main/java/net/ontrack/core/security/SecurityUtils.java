package net.ontrack.core.security;

import net.ontrack.core.model.Account;
import net.ontrack.core.model.Signature;

import java.util.concurrent.Callable;

public interface SecurityUtils {

    boolean isLogged();

    Account getCurrentAccount();

    int getCurrentAccountId();

    Signature getCurrentSignature();

    boolean isAdmin();

    boolean hasRole(String role);

    void checkIsAdmin();

    void checkIsLogged();

    <T> T asAdmin(Callable<T> call);
}
