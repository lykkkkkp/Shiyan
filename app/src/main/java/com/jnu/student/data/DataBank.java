package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String DAILY_TASK_DATA_FILENAME = "daily tasks.data";
    final String WEEKLY_TASK_DATA_FILENAME = "weekly tasks.data";
    final String NORMAL_TASK_DATA_FILENAME = "normal tasks.data";
    final String REWARD_DATA_FILENAME = "rewards.data";
    final String FINISHED_DATA_FILENAME = "finished data.data";

    public ArrayList<DailyTask> LoadDailyTaskItems(Context context) {
        ArrayList<DailyTask> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DAILY_TASK_DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<DailyTask>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "DailyTask loaded successfully. item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    ;

    public void SavaDailyTaskItems(Context context, ArrayList<DailyTask> dailyTaskArrayList) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DAILY_TASK_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream Out = new ObjectOutputStream(fileOut);
            Out.writeObject(dailyTaskArrayList);
            Out.close();
            fileOut.close();
            Log.d("Serialization", "DailyTask is serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WeeklyTask> LoadWeeklyTaskItems(Context context) {
        ArrayList<WeeklyTask> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(WEEKLY_TASK_DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<WeeklyTask>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "WeeklyTask loaded successfully. item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return data;
    }

    ;

    public void SavaWeeklyTaskItems(Context context, ArrayList<WeeklyTask> weeklyTaskArrayList) {
        try {
            FileOutputStream fileOut = context.openFileOutput(WEEKLY_TASK_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream Out = new ObjectOutputStream(fileOut);
            Out.writeObject(weeklyTaskArrayList);
            Out.close();
            fileOut.close();
            Log.d("Serialization", "WeeklyTask is serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NormalTask> LoadNormalTaskItems(Context context) {
        ArrayList<NormalTask> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(NORMAL_TASK_DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<NormalTask>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "NormalTask loaded successfully. item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    ;

    public void SavaNormalTaskItems(Context context, ArrayList<NormalTask> normalTaskArrayList) {
        try {
            FileOutputStream fileOut = context.openFileOutput(NORMAL_TASK_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream Out = new ObjectOutputStream(fileOut);
            Out.writeObject(normalTaskArrayList);
            Out.close();
            fileOut.close();
            Log.d("Serialization", "NormalTask is serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Reward> LoadRewardItems(Context context) {
        ArrayList<Reward> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(REWARD_DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<Reward>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "Reward loaded successfully. item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    ;

    public void SavaRewardItems(Context context, ArrayList<Reward> rewardArrayList) {
        try {
            FileOutputStream fileOut = context.openFileOutput(REWARD_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream Out = new ObjectOutputStream(fileOut);
            Out.writeObject(rewardArrayList);
            Out.close();
            fileOut.close();
            Log.d("Serialization", "Reward is serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TaskFinishedData LoadFinishedDataItems(Context context) {
        TaskFinishedData data = new TaskFinishedData();
        try {
            FileInputStream fileIn = context.openFileInput(FINISHED_DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (TaskFinishedData) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "FinishedData loaded successfully. item count" + data.itemArraylist.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    ;

    public void SavaFinishedDataItems(Context context, TaskFinishedData taskFinishedData) {
        try {
            FileOutputStream fileOut = context.openFileOutput(FINISHED_DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream Out = new ObjectOutputStream(fileOut);
            Out.writeObject(taskFinishedData);
            Out.close();
            fileOut.close();
            Log.d("Serialization", "FinishedData is serialized and saved. item count" + taskFinishedData.itemArraylist.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BookItem> LoadBookItems(FragmentActivity requireActivity) {
        return null;
    }

    public void SaveBookItems(FragmentActivity requireActivity, ArrayList<BookItem> bookItems) {
    }
}


