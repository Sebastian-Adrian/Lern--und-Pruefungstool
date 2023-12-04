package model;

abstract class FrageFactory {

    public abstract MultipleChoiceCheckFrage createMultipleChoiceCheckFrage();

    public abstract MultipleChoiceSelectFrage createMultipleChoiceSelectFrage();

    public static FrageFactory getFactory(String fragenTyp) {

        FrageFactory frage = null;

        switch (fragenTyp) {



        }
        return frage;
    }
}
