package com.nurseify.app.screen.facility.viewModel;

public class DialogStatusMessage {

    DialogStatus dialogStatus;
    int dialogType;

    public DialogStatusMessage(DialogStatus dialogStatus, int message) {
        this.dialogStatus = dialogStatus;
        this.dialogType = message;
    }

    public DialogStatus getDialogStatus() {
        return dialogStatus;
    }

    public void setDialogStatus(DialogStatus dialogStatus) {
        this.dialogStatus = dialogStatus;
    }

    public int getDialogType() {
        return dialogType;
    }

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }
}
