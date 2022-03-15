export class FcError {
    errorCode: string | undefined;
    errorMessage: string | undefined;

    constructor(errorCode: string, errorMessage: string) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}