#include <stdio.h>
#include <string.h>
#include <openssl/evp.h>

void hex_string_to_bytes(const char *hex_string, unsigned char *bytes, size_t hex_string_len) {
    for (size_t i = 0; i < hex_string_len / 2; i++) {
        sscanf(&hex_string[2 * i], "%2hhx", &bytes[i]);
    }
}

int main() {
    // Define the provided plaintext, ciphertext, and IV
    const char *plaintext = "This is a secret tool";
    const char *ciphertext_hex = "ece6753e938f8f903cabbbe12d395bf5f7eae38ad918a2d3e1c3a832476d5c7a";
    const char *iv_hex = "010203040506070809000a0b0c0d0e0f";

    // Load the English word list from a file (words.txt)
    FILE *wordlist = fopen("words.txt", "r");
    if (wordlist == NULL) {
        perror("Failed to open wordlist file");
        return 1;
    }

    char word[16];
    while (fgets(word, sizeof(word), wordlist)) {
        // Remove newline characters from the word
        word[strcspn(word, "\n")] = '\0';

        // Initialize OpenSSL's EVP context
        EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
        EVP_CIPHER_CTX_init(ctx);

        // Convert IV and ciphertext from hex strings to bytes
        unsigned char iv[16];
        unsigned char ciphertext[128];
        hex_string_to_bytes(iv_hex, iv, 32);
        hex_string_to_bytes(ciphertext_hex, ciphertext, 128);

        // Pad the word with pound signs (#) to form a 128-bit key
        unsigned char key[16];
        memset(key, '#', sizeof(key));

        // Copy the word into the key, ensuring it doesn't exceed the key size
        size_t word_len = strlen(word);
        if (word_len > 16) {
            word_len = 16;
        }
        memcpy(key, word, word_len);

        // Set up the decryption parameters
        EVP_DecryptInit_ex(ctx, EVP_aes_128_cbc(), NULL, key, iv);

        // Decrypt the ciphertext
        unsigned char decrypted[128];
        int decrypted_len;
        EVP_DecryptUpdate(ctx, decrypted, &decrypted_len, ciphertext, 128);
        EVP_DecryptFinal_ex(ctx, decrypted + decrypted_len, &decrypted_len);

        // Check if the decrypted plaintext matches the given plaintext
        if (strncmp((char *)decrypted, plaintext, strlen(plaintext)) == 0) {
            printf("Found the key: %s\n", word);
            break;
        }

        EVP_CIPHER_CTX_free(ctx);
    }

    fclose(wordlist);
    return 0;
}
