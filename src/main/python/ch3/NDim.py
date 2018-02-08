import numpy as np
A = np.array([1,2,3,4])
print(A)
print(np.ndim(A))
print(A.shape)
print(A.shape[0])

B = np.array([[1,2],[3,4],[5,6]])
print(B)
print(np.ndim(B))
print(B.shape)

print("dot 2x2")
A = np.array([[1,2],[3,4]])
print(A.shape)
B = np.array([[5,6],[7,8]])
print(B.shape)
print(np.dot(A,B))

print("dot 2x3 or 3x2")
A = np.array([[1,2,3],[4,5,6]])
print(A.shape)
B = np.array([[1,2],[3,4],[5,6]])
print(B.shape)
print("3x2: ", np.dot(A,B))
print("2x3: ", np.dot(B,A))

def sigmoid(x):
    return 1 / (1+np.exp(-x))

print("Pre sigmoid")
X = np.array([1.0,0.5])
W1 = np.array([[0.1,0.3,0.5],[0.2,0.4,0.6]])
B1 = np.array([0.1,0.2,0.3])
print(W1.shape)
print(X.shape)
print(B1.shape)

A1 = np.dot(X,W1) + B1
Z1 = sigmoid(A1)
print(A1)
print(Z1)

W2 = np.array([[0.1,0.4],[0.2,0.5],[0.3,0.6]])
B2 = np.array([0.1,0.2])
print(Z1.shape)
print(W2.shape)
print(B2.shape)
A2 = np.dot(Z1,W2)
Z2 = sigmoid(A2)
print(A2)
print(Z2)