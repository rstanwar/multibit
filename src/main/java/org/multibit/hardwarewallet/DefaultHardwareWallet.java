package org.multibit.hardwarewallet;

import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bsol.trezorj.core.Trezor;
import uk.co.bsol.trezorj.core.protobuf.TrezorMessage;

/**
 * Default class used to wrap an actual hardware wallet and keep track of its state
 */
public  class DefaultHardwareWallet implements HardwareWallet {

    private Logger log = LoggerFactory.getLogger(DefaultHardwareWallet.class);

    private static final String DUMMY_SERIAL_ID = "123456";
    /**
     * Whether or not the actual device is connected or not.
     */
    private boolean connected;

    /**
     * Whether or not the actual device is initialised or not.
     */
    private boolean initialised;

    /**
     * The serial id for the device
     */
    private String serialId;

    private Trezor implementation;

    public DefaultHardwareWallet(Trezor trezor) {
        implementation = trezor;
        initialised = false;
    }

    @Override
    public void setConnected(boolean isConnected) {
        connected = isConnected;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void initialise() {
        if (implementation != null) {

            // TODO Create random session ID (TrezorClients)
            // Send the real message
            ByteString sessionId =  ByteString.copyFrom("123456".getBytes());
            implementation.sendMessage(TrezorMessage.Initialize.newBuilder().setSessionId(sessionId).build());

            serialId = sessionId.toStringUtf8();

        }
    }

    /**
     * Boolean indicating whether the device is initialised or not
     */
    @Override
    public boolean isInitialised() {
        return initialised;
    }

    @Override
    public void setSerialId(String serialId) {
       this.serialId = serialId;
    }

    @Override
    public String getSerialId() {
        return serialId;
    }

    @Override
    public void setInitialised(boolean initialised) {
        this.initialised = initialised;
    }

    /**
     *
     * @return Trezor The physical device that this class wraps.
     * (In the future this will be a more general class, or perhaps Object)
     */
    public Trezor getTrezorClient() {
        return implementation;
    }
}
