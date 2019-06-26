package io.santander.gastos.enumerators;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ErrorMessages {

    MISSING_PARAMETER {
        @Override
        public String getErrorMessage(String error) {
            return String.format("The %s parameter was not found.", error);
        }
    },

    INTERNAL_SERVER_ERROR {
        @Override
        public String getErrorMessage(String error) {
            return String.format("%s", error);
        }
    };

    public abstract String getErrorMessage(String error);
}
