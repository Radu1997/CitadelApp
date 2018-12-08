import numpy as np
import pandas as pd
from sklearn.preprocessing import LabelEncoder
from scipy import spatial
import sqlite3

#create connection to the database
def create_connection(db_file):
    try:
        conn = sqlite3.connect(db_file)
        return conn
    except Error as e:
        print(e)
    return None

database = "locations.db"
conn = create_connection(database)

#some dummy-data to work with until the database-connection is set up
locations = pd.read_csv("locations.csv")
locations = locations.fillna(method='ffill') #fill missing values with placeholders

people = pd.read_csv("people.csv")
people = people.fillna(method='ffill')

#a label-encoder turns the non-numeric values in the table into numbers
enc = LabelEncoder()
people.iloc[:,0] = enc.fit_transform(people.iloc[:,0])
people.iloc[:,2] = enc.fit_transform(people.iloc[:,2])
people.iloc[:,3] = enc.fit_transform(people.iloc[:,3])
people.iloc[:,4] = enc.fit_transform(people.iloc[:,4])
people.iloc[:,5] = enc.fit_transform(people.iloc[:,5])
people.iloc[:,6] = enc.fit_transform(people.iloc[:,6])
people.iloc[:,7] = enc.fit_transform(people.iloc[:,7])
people.iloc[:,8] = enc.fit_transform(people.iloc[:,8])
people.iloc[:,9] = enc.fit_transform(people.iloc[:,9])

locations.iloc[:,0] = enc.fit_transform(locations.iloc[:,0])
locations.iloc[:,1] = enc.fit_transform(locations.iloc[:,1])
locations.iloc[:,4] = enc.fit_transform(locations.iloc[:,4])
locations.iloc[:,5] = enc.fit_transform(locations.iloc[:,5])
locations.iloc[:,7] = enc.fit_transform(locations.iloc[:,7])
locations.iloc[:,8] = enc.fit_transform(locations.iloc[:,8])

#weights: relevance-factor assigned to each feature
weights = np.array([10,1,1,2,5,5,5,5,3,4])

#features- and weight-vectors are multiplied with one another to get user-data
user_data = people.mul(weights,axis=1)

#function that computes the distance between user-vector and location-vectors
result_1 = 1 - spatial.distance.cosine(user_data.iloc[0], locations.iloc[3])
result_2 = 1 - spatial.distance.cosine(user_data.iloc[0], locations.iloc[2])
result_3 = 1 - spatial.distance.cosine(user_data.iloc[0], locations.iloc[1])
#example: user with index 1 is compared to 3 different locations, the location with the
#smallest distance to the user is the likeliest to fit his taste(in this case, result_1)
print(result_1)
print(result_2)
print(result_3)
