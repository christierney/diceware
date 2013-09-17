# diceware

A Clojure script to generate [Diceware][0] passphrases using Java's
[`Secure Random`][1]. As recommended in the [Diceware FAQ][2] this script uses
a special wordlist that is a whole power of 2 in length.

[0]: http://world.std.com/~reinhold/diceware.html
[1]: http://docs.oracle.com/javase/7/docs/api/java/security/SecureRandom.html
[2]: http://world.std.com/~reinhold/dicewarefaq.html#computer

## Usage

    lein run -- [OPTIONS]

    Switches                                 Default  Desc
    --------                                 -------  ----
    -h, --no-help, --help                    false    Show this help message
    -w, --words                              5        Number of words in the passphrase
    -s, --no-special-char, --special-char    false    Require a special character
    -d, --no-digit, --digit                  false    Require a digit

When you use your passphrase you may choose to include spaces between the words
or not; the Diceware page [recommends][3] not to. Spaces are included in the
output to make it easier to read and memorize the passphrase.

It is recommended to generate passphrases of at least 5 words (the default).

[3]: http://world.std.com/~reinhold/dicewarefaq.html#spaces

## Known Issues

* The original word list contains some words with special characters or digits,
  so at the moment even if you pass --no-special-char or --no-digit you may get
  some. The only purpose of the switches is for cases where special chars or
  digits are required. (The --no variants of the switches are added by the cli
  library.)

* The digit filter and the special character filter are applied independently so
  if you require both it is possible that a special character will subsequently
  be replaced with a digit (or vice-versa).

* There aren't any tests.

## License

Copyright © 2013 Chris Tierney.

Distributed under the [Eclipse Public License](LICENSE).
