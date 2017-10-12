Implementation of a visual cryptography algorithm (more about it here: http://users.telenet.be/d.rijmenants/en/visualcrypto.htm).

Mask takes a picture and creates two layers while Unmask overlays them to get back the content of the original image. While it works for both black-and-white and color pictures, it might not give a satisfying result for second ones.

Unmask assumes, that both pictures given as input have the same size.
