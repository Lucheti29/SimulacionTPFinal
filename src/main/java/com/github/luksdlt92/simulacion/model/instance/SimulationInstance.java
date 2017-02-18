package com.github.luksdlt92.simulacion.model.instance;

public class SimulationInstance {

    private final int mQAPeopleAmount;
    private final int[] mProjectsAmount;
    private final int[][] mTechnologySeniorities;

    private SimulationInstance(int peopleAmount, int[] projectsAmount, int[][] technologySeniorities) {
        mQAPeopleAmount = peopleAmount;
        mProjectsAmount = projectsAmount;
        mTechnologySeniorities = technologySeniorities;
    }

    public void run() {
        System.out.println("Running");
    }

    public static class Builder {

        private int mQAPeopleAmount;
        private int[] mProjectsAmount = new int[3];
        private int[][] mTechnologySeniorities = new int[3][3];

        private Builder() {}

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setQAPeopleAmount(int peopleAmount) {
            mQAPeopleAmount = peopleAmount;
            return this;
        }

        public Builder setProjectsAmount(int technology, int projectsAmount) {
            mProjectsAmount[technology] = projectsAmount;
            return this;
        }

        public Builder setSeniorityAmount(int technology, int seniority, int amount) {
            mTechnologySeniorities[technology][seniority] = amount;
            return this;
        }

        public SimulationInstance build() {
            System.out.println("La cantidad de gente de QA es " + mQAPeopleAmount);

            for (int i = 0; i < mProjectsAmount.length; i++) {
                System.out.println("En la tecnologia " + i + " hay " + mProjectsAmount[i]);
            }

            for (int i = 0; i < mTechnologySeniorities.length; i++) {
                for (int i2 = 0; i2 < mTechnologySeniorities[i].length; i2++) {
                    System.out.println("En la tecnologia " + i + " hay " + mTechnologySeniorities[i][i2] + " de nivel " + i2);
                }
            }

            return new SimulationInstance(mQAPeopleAmount, mProjectsAmount, mTechnologySeniorities);
        }
    }

}
