import org.example.Logger

def logger = new Logger(out)
logger.info('example info message (black color)')
logger.warn('example multiline warn message\nnext line is also yellow')
logger.error('example error message (red color)')
