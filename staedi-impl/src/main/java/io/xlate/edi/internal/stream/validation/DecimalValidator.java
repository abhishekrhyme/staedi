/*******************************************************************************
 * Copyright 2017 xlate.io LLC, http://www.xlate.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package io.xlate.edi.internal.stream.validation;

class DecimalValidator extends NumericValidator {

    private static final DecimalValidator singleton = new DecimalValidator();

    private DecimalValidator() {
    }

    static DecimalValidator getInstance() {
        return singleton;
    }

    @Override
    int validate(CharSequence value) {
        int length = value.length();

        int dec = 0;
        int exp = 0;
        boolean invalid = false;

        for (int i = 0, m = length; i < m; i++) {
            switch (value.charAt(i)) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                break;

            case '.':
                length--;

                if (++dec > 1 || exp > 0) {
                    invalid = true;
                }
                break;

            case 'E':
                length--;

                if (++exp > 1) {
                    invalid = true;
                }
                break;

            case '-':
                length--;

                if (i > 0 && value.charAt(i - 1) != 'E') {
                    invalid = true;
                }
                break;

            default:
                invalid = true;
                break;
            }
        }

        return invalid ? -length : length;
    }
}
