package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import ru.ifmo.android_2016.calc.R.id;
import ru.ifmo.android_2016.calc.R.layout;
import ru.ifmo.android_2016.calc.R.string;

public class MainActivity extends Activity implements OnTouchListener {

    private TextView output;
    private View btnAllClear;
    private View btnClear;

    private boolean wasSecond;
    private Operation activeOperation = Operation.NOTHING;

    private double first;
    private double second;

    private final Collection<Button> buttons = new ArrayList<>();
    private final Map<Operation, Button> operationButtons = new EnumMap<>(Operation.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.main);
        findAllButtonsAndListen();
        output = (TextView) findViewById(id.output);
        output.setText("0");
        registerForContextMenu(output);
    }

    private void findAllButtonsAndListen() {
        btnAllClear = registerAndAddButtonToList(id.btnAllClear);
        btnClear = registerAndAddButtonToList(id.btnClear);
        operationButtons.put(Operation.DIV, registerAndAddButtonToList(id.btnDiv));
        operationButtons.put(Operation.MUL, registerAndAddButtonToList(id.btnMul));
        operationButtons.put(Operation.SUB, registerAndAddButtonToList(id.btnSub));
        operationButtons.put(Operation.ADD, registerAndAddButtonToList(id.btnAdd));

        Iterable<Integer> buttonIdsToRegister =
                Arrays.asList(id.btnSign, id.btnSeven, id.btnEight, id.btnNine,
                        id.btnFour, id.btnFive, id.btnSix,
                        id.btnOne, id.btnTwo, id.btnThree,
                        id.btnZero, id.btnPoint, id.btnEquals);

        for (Integer id : buttonIdsToRegister) {
            registerAndAddButtonToList(id);
        }

        for (View button : buttons) {
            button.setOnTouchListener(this);
        }

        changeClearButtonsState(false);
    }

    private void changeClearButtonsState(boolean state) {
        btnClear.setEnabled(state);
        btnAllClear.setEnabled(state);
    }

    private Button registerAndAddButtonToList(int id) {
        Button button = (Button) findViewById(id);
        buttons.add(button);
        return button;
    }

    private void showToast(CharSequence msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void deleteOneCharFromOutput() {
        output.setText(output.getText().subSequence(0, output.getText().length() - 1));
    }

    private void disableOperationButtons() {
        for (View button : operationButtons.values()) {
            button.setPressed(false);
        }
    }

    private boolean isZeroOutput() {
        return "0".equals(output.getText().toString());
    }

    private boolean checkZeroDivision() {
        if (Math.abs(second) < 1e-9) {
            showToast("Division by zero");
            resetAll();
            return true;
        }

        return false;
    }

    private void resetAll() {
        output.setText("0");
        changeClearButtonsState(false);
        wasSecond = false;
        activeOperation = Operation.NOTHING;
        disableOperationButtons();
    }

    private boolean clearButtonPressed() {
        deleteOneCharFromOutput();

        if (output.getText().toString().isEmpty() || "-".equals(output.getText().toString())) {
            output.setText("0");
            btnClear.setEnabled(false);
            wasSecond = false;

            if (activeOperation == Operation.NOTHING) {
                btnAllClear.setEnabled(false);
            }
        }

        return false;
    }

    private boolean allClearButtonPressed() {
        resetAll();
        return false;
    }

    private boolean operationButtonPressed(int buttonId) {
        Utils.normalizeOutput(output);

        if (!wasSecond) {
            first = Double.parseDouble(output.getText().toString());
            output.setText("0");
            wasSecond = true;
            btnClear.setEnabled(false);
        }

        switch (buttonId) {
            case id.btnAdd:
                activeOperation = Operation.ADD;
                break;
            case id.btnSub:
                activeOperation = Operation.SUB;
                break;
            case id.btnMul:
                activeOperation = Operation.MUL;
                break;
            case id.btnDiv:
                activeOperation = Operation.DIV;
                break;
            default:
                activeOperation = Operation.NOTHING;
        }

        if (activeOperation != Operation.NOTHING) {
            operationButtons.get(activeOperation).setPressed(true);
        }

        return true;
    }

    private boolean signButtonPressed() {
        if (isZeroOutput()) {
            showToast("Zero has no sign");
            return false;
        }

        if (output.getText().charAt(0) != '-') {
            CharSequence negativeNumber = '-' + output.getText().toString();
            output.setText(negativeNumber);
            changeClearButtonsState(true);
        } else {
            output.setText(output.getText().toString().substring(1));
        }

        return false;
    }

    private boolean equalsButtonPressed() {
        if (!wasSecond) {
            Utils.normalizeOutput(output);
            CharSequence result = Utils.deleteTrailingZeros(output.getText().toString());
            output.setText(result);
            first = Double.parseDouble(output.getText().toString());
            return false;
        }

        second = Double.parseDouble(output.getText().toString());
        wasSecond = false;
        double answer;

        switch (activeOperation) {
            case ADD:
                answer = first + second;
                break;
            case SUB:
                answer = first - second;
                break;
            case MUL:
                answer = first * second;
                break;
            case DIV:
                if (checkZeroDivision()) {
                    return false;
                }

                answer = first / second;
                break;
            default:
                throw new IllegalStateException("State is impossible!");
        }

        CharSequence result = String.format(Locale.ENGLISH, "%.10f", answer);
        result = Utils.deleteTrailingZeros(result);
        output.setText(result);

        if (isZeroOutput()) {
            resetAll();
        } else {
            operationButtons.get(activeOperation).setPressed(false);
            activeOperation = Operation.NOTHING;
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }

        switch (v.getId()) {
            case id.btnClear:
                return clearButtonPressed();
            case id.btnAllClear:
                return allClearButtonPressed();
            case id.btnAdd:
            case id.btnSub:
            case id.btnMul:
            case id.btnDiv:
                return operationButtonPressed(v.getId());
            case id.btnSign:
                return signButtonPressed();
            case id.btnEquals:
                return equalsButtonPressed();
        }

        CharSequence toAppend = ((TextView) v).getText().toString();

        if (isZeroOutput() && !"0".equals(toAppend)) {
            changeClearButtonsState(true);

            if (".".equals(toAppend)) {
                output.append(toAppend);
            } else {
                output.setText(toAppend);
            }

            return false;
        }

        if (!isZeroOutput() && (!".".equals(toAppend) || !output.getText().toString().contains("."))) {
            output.append(toAppend);
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(InstanceStateMessages.OUTPUT.name(), output.getText().toString());
        outState.putBoolean(InstanceStateMessages.BTN_CLEAR.name(), btnClear.isEnabled());
        outState.putBoolean(InstanceStateMessages.BTN_ALL_CLEAR.name(), btnAllClear.isEnabled());
        outState.putSerializable(InstanceStateMessages.ACTIVE_OPERATION.name(), activeOperation);
        outState.putDouble(InstanceStateMessages.FIRST.name(), first);
        outState.putDouble(InstanceStateMessages.SECOND.name(), second);
        outState.putBoolean(InstanceStateMessages.WAS_SECOND.name(), wasSecond);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        output.setText(savedInstanceState.getString(InstanceStateMessages.OUTPUT.name()));
        btnClear.setEnabled(savedInstanceState.getBoolean(InstanceStateMessages.BTN_CLEAR.name()));
        btnAllClear.setEnabled(savedInstanceState.getBoolean(InstanceStateMessages.BTN_ALL_CLEAR.name()));
        activeOperation = (Operation) savedInstanceState.getSerializable(InstanceStateMessages.ACTIVE_OPERATION.name());
        first = savedInstanceState.getDouble(InstanceStateMessages.FIRST.name());
        second = savedInstanceState.getDouble(InstanceStateMessages.SECOND.name());
        wasSecond = savedInstanceState.getBoolean(InstanceStateMessages.WAS_SECOND.name());

        if (activeOperation != Operation.NOTHING) {
            operationButtons.get(activeOperation).setEnabled(true);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        if (v.getId() == id.output) {
            menu.add(0, 0, 0, string.clear_all);
        }

        if (isZeroOutput() && activeOperation == Operation.NOTHING) {
            menu.setGroupVisible(0, false);
        } else {
            menu.setGroupVisible(0, true);
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            resetAll();
        }

        return super.onContextItemSelected(item);
    }

    private enum InstanceStateMessages {
        OUTPUT,
        BTN_CLEAR,
        BTN_ALL_CLEAR,
        ACTIVE_OPERATION,
        FIRST,
        SECOND,
        WAS_SECOND
    }
}