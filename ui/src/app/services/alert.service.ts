import { Injectable } from "@angular/core";
import {
    ConfirmBoxInitializer,
    ToastNotificationInitializer,
    IToastNotificationPublicResponse,
    DialogLayoutDisplay,
    IConfirmBoxPublicResponse,
    ToastPositionEnum,
    ToastUserViewTypeEnum
} from "@costlydeveloper/ngx-awesome-popup";
import { ToastNotificationService } from "@costlydeveloper/ngx-awesome-popup/ngx-awesome-popup/types/toast-notification/core/toast-notification.service";
import { ToastNotificationSimpleWrapperComponent } from "@costlydeveloper/ngx-awesome-popup/ngx-awesome-popup/types/toast-notification/toast-notification-simple-wrapper/toast-notification-simple-wrapper.component";
import { FcError } from "../messages/fcerror";



@Injectable({ providedIn: 'root' })
export class AlertService {
    public response: any;

    constructor() {

    }

    showToast(message: string, layoutType: DialogLayoutDisplay, autoCloseMillis: number) {
        const toast = new ToastNotificationInitializer();
        toast.setMessage(message);
        toast.setConfig({
            ToastPosition: ToastPositionEnum.TOP_CENTER,
            ToastUserViewType: ToastUserViewTypeEnum.SIMPLE,
            LayoutType: layoutType,
            AutoCloseDelay: autoCloseMillis
        });

        const toastSubscription = toast.openToastNotification$().subscribe(resp => {
            this.response = resp;
            toastSubscription.unsubscribe;
        })
        return this.response;

    }

    showAlert(message: string, confirmBtnLabel: string, alertType: DialogLayoutDisplay): IConfirmBoxPublicResponse {
        const alertBox = new ConfirmBoxInitializer();
        alertBox.setMessage(message);
        alertBox.setButtonLabels(confirmBtnLabel);
        alertBox.setConfig({
            LayoutType: alertType
        });

        const subscription = alertBox.openConfirmBox$().subscribe(resp => {

            this.response = resp;
            subscription.unsubscribe();

        });
        return this.response;
    }

    showErrorAlert(error: FcError, confirmBtnLabel: string, alertType: DialogLayoutDisplay): IConfirmBoxPublicResponse {
        const alertBox = new ConfirmBoxInitializer();
        alertBox.setMessage("Error code: " + error.errorCode + " \n " + " Error message: " + error.errorMessage);
        alertBox.setButtonLabels(confirmBtnLabel);
        alertBox.setConfig({
            LayoutType: alertType
        });

        const subscription = alertBox.openConfirmBox$().subscribe(resp => {
            this.response = resp;
            subscription.unsubscribe();
        });

        return this.response;
    }

}