package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener {

    private Button btnAllClear;
    private Button btnClear;
    private Button btnDiv;
    private Button btnMul;
    private Button btnSub;
    private Button btnAdd;
    private TextView output;
    private boolean disabledAllClear = true;
    private boolean disabledClear = true;
    private boolean activatedAdd = false;
    private boolean activatedSub = false;
    private boolean activatedMul = false;
    private boolean activatedDiv = false;
    private boolean onOperation = false;
    private boolean wasSecond = false;
    private double first;
    private double second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findAllButtonsAndListen();
        output = (TextView) findViewById(R.id.output);
        output.setText("0");
        registerForContextMenu(output);
    }

    private void findAllButtonsAndListen() {
        btnAllClear = (Button) findViewById(R.id.btnAllClear);
        btnClear = (Button) findViewById(R.id.btnClear);
        Button btnSign = (Button) findViewById(R.id.btnSign);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        Button btnSeven = (Button) findViewById(R.id.btnSeven);
        Button btnEight = (Button) findViewById(R.id.btnEight);
        Button btnNine = (Button) findViewById(R.id.btnNine);
        btnMul = (Button) findViewById(R.id.btnMul);
        Button btnFour = (Button) findViewById(R.id.btnFour);
        Button btnFive = (Button) findViewById(R.id.btnFive);
        Button btnSix = (Button) findViewById(R.id.btnSix);
        btnSub = (Button) findViewById(R.id.btnSub);
        Button btnOne = (Button) findViewById(R.id.btnOne);
        Button btnTwo = (Button) findViewById(R.id.btnTwo);
        Button btnThree = (Button) findViewById(R.id.btnThree);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnZero = (Button) findViewById(R.id.btnZero);
        Button btnPoint = (Button) findViewById(R.id.btnPoint);
        Button btnEquals = (Button) findViewById(R.id.btnEquals);

        btnAllClear.setOnTouchListener(this);
        btnClear.setOnTouchListener(this);
        btnSign.setOnTouchListener(this);
        btnDiv.setOnTouchListener(this);
        btnSeven.setOnTouchListener(this);
        btnEight.setOnTouchListener(this);
        btnNine.setOnTouchListener(this);
        btnMul.setOnTouchListener(this);
        btnFour.setOnTouchListener(this);
        btnFive.setOnTouchListener(this);
        btnSix.setOnTouchListener(this);
        btnSub.setOnTouchListener(this);
        btnOne.setOnTouchListener(this);
        btnTwo.setOnTouchListener(this);
        btnThree.setOnTouchListener(this);
        btnAdd.setOnTouchListener(this);
        btnZero.setOnTouchListener(this);
        btnPoint.setOnTouchListener(this);
        btnEquals.setOnTouchListener(this);
        btnAllClear.setEnabled(false);
        btnClear.setEnabled(false);
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }

        if (v.getId() == R.id.btnClear) {
            output.setText(output.getText().toString().substring(0, output.getText().length() - 1));

            if (output.getText().toString().equals("") || output.getText().toString().equals("-")) {
                output.setText("0");
                disabledClear = true;
                btnClear.setEnabled(false);
                wasSecond = false;

                if (!onOperation) {
                    disabledAllClear = true;
                    btnAllClear.setEnabled(false);
                    return false;
                }

                onOperation = false;
            }

            return false;
        }

        if (v.getId() == R.id.btnAllClear) {
            output.setText("0");
            disabledClear = true;
            btnClear.setEnabled(false);
            disabledAllClear = true;
            btnAllClear.setEnabled(false);
            wasSecond = false;
            onOperation = false;
            activatedAdd = false;
            activatedSub = false;
            activatedMul = false;
            activatedDiv = false;
            btnAdd.setPressed(false);
            btnSub.setPressed(false);
            btnMul.setPressed(false);
            btnDiv.setPressed(false);
            return false;
        }

        if (v.getId() == R.id.btnAdd) {
            if (output.getText().charAt(output.getText().length() - 1) == '.') {
                output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
            }

            if (!wasSecond) {
                first = Double.parseDouble(output.getText().toString());
                output.setText("0");
                wasSecond = true;
                disabledClear = true;
                btnClear.setEnabled(false);
            }

            onOperation = true;
            activatedAdd = true;
            activatedSub = false;
            activatedMul = false;
            activatedDiv = false;
            btnAdd.setPressed(true);
            btnSub.setPressed(false);
            btnMul.setPressed(false);
            btnDiv.setPressed(false);
            return true;
        }

        if (v.getId() == R.id.btnSub) {
            if (output.getText().charAt(output.getText().length() - 1) == '.') {
                output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
            }

            if (!wasSecond) {
                first = Double.parseDouble(output.getText().toString());
                output.setText("0");
                wasSecond = true;
                disabledClear = true;
                btnClear.setEnabled(false);
            }

            onOperation = true;
            activatedAdd = false;
            activatedSub = true;
            activatedMul = false;
            activatedDiv = false;
            btnAdd.setPressed(false);
            btnSub.setPressed(true);
            btnMul.setPressed(false);
            btnDiv.setPressed(false);
            return true;
        }

        if (v.getId() == R.id.btnMul) {
            if (output.getText().charAt(output.getText().length() - 1) == '.') {
                output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
            }

            if (!wasSecond) {
                first = Double.parseDouble(output.getText().toString());
                output.setText("0");
                wasSecond = true;
                disabledClear = true;
                btnClear.setEnabled(false);
            }

            onOperation = true;
            activatedAdd = false;
            activatedSub = false;
            activatedMul = true;
            activatedDiv = false;
            btnAdd.setPressed(false);
            btnSub.setPressed(false);
            btnMul.setPressed(true);
            btnDiv.setPressed(false);
            return true;
        }

        if (v.getId() == R.id.btnDiv) {
            if (output.getText().charAt(output.getText().length() - 1) == '.') {
                output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
            }

            if (!wasSecond) {
                first = Double.parseDouble(output.getText().toString());
                output.setText("0");
                wasSecond = true;
                disabledClear = true;
                btnClear.setEnabled(false);
            }

            onOperation = true;
            activatedAdd = false;
            activatedSub = false;
            activatedMul = false;
            activatedDiv = true;
            btnAdd.setPressed(false);
            btnSub.setPressed(false);
            btnMul.setPressed(false);
            btnDiv.setPressed(true);
            return true;
        }

        if (v.getId() == R.id.btnSign) {
            if (output.getText().charAt(0) != '-') {
                String sign = "-" + output.getText().toString();
                output.setText(sign);
                disabledAllClear = false;
                btnAllClear.setEnabled(true);
                disabledClear = false;
                btnClear.setEnabled(true);
            } else {
                output.setText(output.getText().toString().substring(1));

                if (output.getText().toString().isEmpty()) {
                    disabledAllClear = true;
                    btnAllClear.setEnabled(false);
                    disabledClear = true;
                    btnClear.setEnabled(false);
                }
            }

            return false;
        }

        if (v.getId() == R.id.btnEquals) {
            if (!wasSecond) {
                if (output.getText().charAt(output.getText().length() - 1) == '.') {
                    output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
                }

                first = Double.parseDouble(output.getText().toString());
                String res = String.valueOf(first);
                res = res.replace(',', '.');
                int k = res.length();

                for (int i = k - 1; i > -1; i--) {
                    if (res.charAt(i) == '0') {
                        k--;
                        continue;
                    }

                    break;
                }

                if (res.charAt(k - 1) == '.') {
                    k--;
                }

                res = res.substring(0, k);
                output.setText(res);

                return false;
            }

            second = Double.parseDouble(output.getText().toString());
            wasSecond = false;
            onOperation = false;

            if (activatedAdd) {
                double ans = first + second;
                first = 0.0;
                String res = String.valueOf(ans);
                res = res.replace(',', '.');
                int k = res.length();

                for (int i = k - 1; i > -1; i--) {
                    if (res.charAt(i) == '0') {
                        k--;
                        continue;
                    }

                    break;
                }

                if (res.charAt(k - 1) == '.') {
                    k--;
                }

                res = res.substring(0, k);
                output.setText(res);
                activatedAdd = false;
                btnAdd.setPressed(false);
            } else if (activatedSub) {
                double ans = first - second;
                first = 0.0;
                String res = String.valueOf(ans);
                res = res.replace(',', '.');
                int k = res.length();

                for (int i = k - 1; i > -1; i--) {
                    if (res.charAt(i) == '0') {
                        k--;
                        continue;
                    }

                    break;
                }

                if (res.charAt(k - 1) == '.') {
                    k--;
                }

                res = res.substring(0, k);
                output.setText(res);
                activatedSub = false;
                btnSub.setPressed(false);
            } else if (activatedMul) {
                double ans = first * second;
                first = 0.0;
                String res = String.valueOf(ans);
                res = res.replace(',', '.');
                int k = res.length();

                for (int i = k - 1; i > -1; i--) {
                    if (res.charAt(i) == '0') {
                        k--;
                        continue;
                    }

                    break;
                }

                if (res.charAt(k - 1) == '.') {
                    k--;
                }

                res = res.substring(0, k);
                output.setText(res);
                activatedMul = false;
                btnMul.setPressed(false);
            } else {
                if (Math.abs(second) < 1e-9) {
                    showToast("Division by zero");
                    output.setText("0");
                    disabledAllClear = true;
                    btnAllClear.setEnabled(false);
                    disabledClear = true;
                    btnClear.setEnabled(false);
                    activatedDiv = false;
                    btnDiv.setPressed(false);
                    return false;
                }

                double ans = first / second;
                first = 0.0;
                String res = String.valueOf(ans);
                res = res.replace(',', '.');
                int k = res.length();

                for (int i = k - 1; i > -1; i--) {
                    if (res.charAt(i) == '0') {
                        k--;
                        continue;
                    }

                    break;
                }

                if (res.charAt(k - 1) == '.') {
                    k--;
                }

                res = res.substring(0, k);
                output.setText(res);
                activatedDiv = false;
                btnDiv.setPressed(false);
            }

            return false;
        }

        String toAppend = ((Button) v).getText().toString();

        if (output.getText().toString().equals("0") && !toAppend.equals("0")) {
            disabledAllClear = false;
            btnAllClear.setEnabled(true);
            disabledClear = false;
            btnClear.setEnabled(true);

            if (toAppend.equals(".")) {
                output.append(toAppend);
            } else {
                output.setText(toAppend);
            }

            return false;
        }

        if (!output.getText().toString().equals("0") && (!toAppend.equals(".") || !output.getText().toString().contains("."))) {
            output.append(toAppend);
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("Output", output.getText().toString());
        outState.putBoolean("Disabled clear", disabledClear);
        outState.putBoolean("Disabled all clear", disabledAllClear);
        outState.putBoolean("Activated add", activatedAdd);
        outState.putBoolean("Activated sub", activatedSub);
        outState.putBoolean("Activated mul", activatedMul);
        outState.putBoolean("Activated div", activatedDiv);
        outState.putDouble("First", first);
        outState.putDouble("Second", second);
        outState.putBoolean("On operation", onOperation);
        outState.putBoolean("Was second", wasSecond);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        output.setText(savedInstanceState.getString("Output"));
        disabledClear = savedInstanceState.getBoolean("Disabled clear");
        disabledAllClear = savedInstanceState.getBoolean("Disabled all clear");
        activatedAdd = savedInstanceState.getBoolean("Activated add");
        activatedSub = savedInstanceState.getBoolean("Activated sub");
        activatedMul = savedInstanceState.getBoolean("Activated mul");
        activatedDiv = savedInstanceState.getBoolean("Activated div");
        first = savedInstanceState.getDouble("First");
        second = savedInstanceState.getDouble("Second");
        onOperation = savedInstanceState.getBoolean("On operation");
        wasSecond = savedInstanceState.getBoolean("Was second");

        if (!disabledClear) {
            btnClear.setEnabled(true);
        }

        if (!disabledAllClear) {
            btnAllClear.setEnabled(true);
        }

        if (activatedAdd) {
            btnAdd.setPressed(true);
        }

        if (activatedSub) {
            btnSub.setPressed(true);
        }

        if (activatedMul) {
            btnMul.setPressed(true);
        }

        if (activatedDiv) {
            btnDiv.setPressed(true);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.output) {
            menu.add(0, 0, 0, R.string.clear_all);
        }

        if (output.getText().toString().equals("0") && !onOperation) {
            menu.setGroupVisible(0, false);
        } else {
            menu.setGroupVisible(0, true);
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            output.setText("0");
            onOperation = false;
            disabledClear = true;
            btnClear.setEnabled(false);
            disabledAllClear = true;
            btnAllClear.setEnabled(false);
        }

        return super.onContextItemSelected(item);
    }
}