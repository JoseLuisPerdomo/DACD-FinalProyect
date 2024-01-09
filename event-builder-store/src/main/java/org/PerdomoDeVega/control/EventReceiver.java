package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import java.util.List;

public interface EventReceiver{
    List<String> ReceiveEvent() throws ReceiverException;
}
