package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;

import java.util.List;

public interface SensorReciever {
    List<List<String>> ReceiveEvent() throws ReceiverException;
}
